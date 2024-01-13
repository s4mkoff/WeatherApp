package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s4mkoff.weatherapp.R
import com.s4mkoff.weatherapp.WeatherApp
import com.s4mkoff.weatherapp.features.weather.domain.model.MyWeather
import com.s4mkoff.weatherapp.features.weather.domain.util.LoadingState
import com.s4mkoff.weatherapp.features.weather.domain.util.NetworkState
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.components.SearchBar
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.components.WeatherCard

@Composable
fun MainScreen(
    state: WeatherState,
    getWeatherByCity: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(
                    id = if (
                        (state.weather?.current?.is_day ?: 1) == 1
                    ) R.drawable.background_day else R.drawable.background_night
                ),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds,
            )
            SearchBar(
                onSearch = { city, _ ->
                    getWeatherByCity(city)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .focusRequester(focusRequester),
                clearFocus = { focusManager.clearFocus() }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .shadow(elevation = 40.dp)
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.weather != null) {
                    Text(
                        text = MyWeather.WeatherHelper.formatTime(
                            state.weather.current.time.toLong(),
                            "General"
                        ),
                        modifier = Modifier.padding(18.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                color = Color(0x140D9FEA),
                                shape = RoundedCornerShape(topEnd = 32.dp, bottomStart = 32.dp)
                            )
                            .padding(18.dp)
                    ) {
                        Text(
                            text = "${state.countryName}, ${state.cityName}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF0DA0EA)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "location"
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .weight(2f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.loading.value) {
                LoadingState.LOADING -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (state.firstLoad && !state.isLocationPermissionGranted) {
                            Text(
                                text = "Can't load data, because of lack of permission or location service disabled. \n But you still can try searching!",
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                        } else {
                            Text(
                                text = "Loading your data",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    }
                }
                LoadingState.SUCCESS -> {
                    if (
                        state.weather != null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = MyWeather.WeatherHelper.weatherCodeToResId(
                                            weatherCode = state.weather.current.weather_code,
                                            isDay = state.weather.current.is_day
                                        )
                                    ),
                                    contentDescription = "Weather Description",
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                                Text(
                                    text = MyWeather.WeatherHelper.weatherCode(
                                        state.weather.current.weather_code
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = state.weather.current.temperature_2m.toInt()
                                        .toString(),
                                    fontSize = 64.sp
                                )
                                Text(
                                    text = state.weather.current_units.temperature_2m,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF666666),
                                    modifier = Modifier.padding(top = 11.dp)
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = state.weather.daily.temperature_2m_max[0].toString() + state.weather.daily_units.temperature_2m_max,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(300),
                                        color = Color(0xFF666666)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.temperature_up),
                                        contentDescription = "Temperature Max",
                                        modifier = Modifier.padding(start = 1.dp),
                                        tint = Color(0xFFAAAAAA)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = state.weather.daily.temperature_2m_min[0].toString() + state.weather.daily_units.temperature_2m_min,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(300),
                                        color = Color(0xFF666666)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.temperature_down),
                                        contentDescription = "Temperature Min",
                                        modifier = Modifier.padding(start = 1.dp),
                                        tint = Color(0xFFAAAAAA)
                                    )
                                }
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.humidity),
                                    contentDescription = "Humidity",
                                    tint = Color(0xFFAAAAAA)
                                )
                                Text(
                                    text = state.weather.current.relative_humidity_2m.toString() + "%",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF444444),
                                )
                                Text(
                                    text = "Humidity",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF999999),
                                    letterSpacing = 0.8.sp
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.barometer),
                                    contentDescription = "Barometer",
                                    tint = Color(0xFFAAAAAA)
                                )
                                Text(
                                    text = state.weather.current.surface_pressure.toString() + state.weather.current_units.surface_pressure,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF444444)
                                )
                                Text(
                                    text = "Pressure",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF999999),
                                    letterSpacing = 0.8.sp
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.wind),
                                    contentDescription = "Wind",
                                    tint = Color(0xFFAAAAAA)
                                )
                                Text(
                                    text = state.weather.current.wind_speed_10m.toString() + state.weather.current_units.wind_speed_10m,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF444444),
                                )
                                Text(
                                    text = "Wind",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF999999),
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sunrise),
                                    contentDescription = "Sunrise",
                                    tint = Color(0xFFAAAAAA)
                                )
                                Text(
                                    text = MyWeather.WeatherHelper.formatTime(
                                        state.weather.daily.sunrise[0].toLong(),
                                        "Sun"
                                    ),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF444444),
                                )
                                Text(
                                    text = "Sunrise",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF999999),
                                    letterSpacing = 0.8.sp
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sunset),
                                    contentDescription = "Sunset",
                                    tint = Color(0xFFAAAAAA)
                                )
                                Text(
                                    text = MyWeather.WeatherHelper.formatTime(
                                        state.weather.daily.sunset[0].toLong(),
                                        "Sun"
                                    ),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF444444),
                                )
                                Text(
                                    text = "Sunset",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF999999),
                                    letterSpacing = 0.8.sp
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.daytime),
                                    contentDescription = "Sand-clock",
                                    tint = Color(0xFFAAAAAA)
                                )
                                Text(
                                    text = MyWeather.WeatherHelper.formatSecondsToHoursMinutes(
                                        state.weather.daily.daylight_duration[0]
                                    ),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF444444),
                                )
                                Text(
                                    text = "Daytime",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF999999),
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }
                        LazyRow(
                            contentPadding = PaddingValues(start = 19.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            items(state.weather.daily.temperature_2m_min.size) {
                                WeatherCard(
                                    imageId = MyWeather.WeatherHelper.weatherCodeToResId(
                                        weatherCode = state.weather.daily.weather_code[it],
                                        isDay = state.weather.current.is_day
                                    ),
                                    date = MyWeather.WeatherHelper.formatTime(
                                        condition = "Card",
                                        time = state.weather.daily.time[it].toLong()
                                    ),
                                    maxTemperature = state.weather.daily.temperature_2m_max[it].toInt()
                                        .toString(),
                                    minTemperature = state.weather.daily.temperature_2m_min[it].toInt()
                                        .toString()
                                )
                            }
                        }
                    }
                }
                LoadingState.ERROR -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error occured",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        )
                        if (WeatherApp.networkState == NetworkState.UNAVAILABLE) {
                            Text(
                                text = "Internet unaviable",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                        }
                    }
                }
            }
        }
    }
}