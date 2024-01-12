package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s4mkoff.weatherapp.R
import com.s4mkoff.weatherapp.WeatherApp
import com.s4mkoff.weatherapp.data.WeatherCodeHelpers
import com.s4mkoff.weatherapp.features.weather.presentation.util.NetworkState
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.components.SearchBar
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.components.WeatherCard

@Composable
fun MainScreen(
    viewModel: WeatherViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_day),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds,
            )
            SearchBar(
                onSearch = { city, ctx ->
                    viewModel.getWeatherByCity(city = city)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
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
                if (
                    viewModel.state.value.weather != null
                ) {
                    Text(
                        text = WeatherCodeHelpers().formatTime(
                            viewModel.state.value.weather!!.current.time.toLong(),
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
                            text = "${viewModel.state.value.countryName}, ${viewModel.state.value.cityName}",
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
            if (
                viewModel.state.value.weather != null
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
                                id = WeatherCodeHelpers().weatherCodeToResId(
                                    weatherCode = viewModel.state.value.weather!!.current.weather_code,
                                    isDay = viewModel.state.value.weather!!.current.is_day
                                )
                            ),
                            contentDescription = "Weather Description",
                            modifier = Modifier
                                .size(40.dp)
                        )
                        Text(
                            text = WeatherCodeHelpers().weatherCodeToString(
                                weatherCode = viewModel.state.value.weather!!.current.weather_code
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
                            text = viewModel.state.value.weather!!.current.temperature_2m.toInt()
                                .toString(),
                            fontSize = 64.sp
                        )
                        Text(
                            text = viewModel.state.value.weather!!.current_units.temperature_2m,
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
                                text = viewModel.state.value.weather!!.daily.temperature_2m_max[0].toString() + viewModel.state.value.weather!!.daily_units.temperature_2m_max,
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
                                text = viewModel.state.value.weather!!.daily.temperature_2m_min[0].toString() + viewModel.state.value.weather!!.daily_units.temperature_2m_min,
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
                            text = viewModel.state.value.weather!!.current.relative_humidity_2m.toString() + "%",
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
                            text = viewModel.state.value.weather!!.current.surface_pressure.toString() + viewModel.state.value.weather!!.current_units.surface_pressure,
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
                            text = viewModel.state.value.weather!!.current.wind_speed_10m.toString() + viewModel.state.value.weather!!.current_units.wind_speed_10m,
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
                            text = WeatherCodeHelpers().formatTime(
                                viewModel.state.value.weather!!.daily.sunrise[0].toLong(),
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
                            text = WeatherCodeHelpers().formatTime(
                                viewModel.state.value.weather!!.daily.sunset[0].toLong(),
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
                            text = WeatherCodeHelpers().formatSecondsToHoursMinutes(
                                viewModel.state.value.weather!!.daily.daylight_duration[0]
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
                    items(viewModel.state.value.weather!!.daily.temperature_2m_min.size) {
                        WeatherCard(
                            imageId = WeatherCodeHelpers().weatherCodeToResId(
                                weatherCode = viewModel.state.value.weather!!.daily.weather_code[it],
                                isDay = viewModel.state.value.weather!!.current.is_day
                            ),
                            date = WeatherCodeHelpers().formatTime(
                                condition = "Card",
                                time = viewModel.state.value.weather!!.daily.time[it].toLong()
                            ),
                            maxTemperature = viewModel.state.value.weather!!.daily.temperature_2m_max[it].toInt()
                                .toString(),
                            minTemperature = viewModel.state.value.weather!!.daily.temperature_2m_min[it].toInt()
                                .toString()
                        )
                    }
                }
            } else {
//                if (viewModel.doFirstSearch) {
//                    Text(
//                        text = "Search your city!",
//                        modifier = Modifier.fillMaxSize(),
//                        textAlign = TextAlign.Center,
//                        fontSize = MaterialTheme.typography.titleLarge.fontSize
//                    )
//                } else
                if (WeatherApp.networkState == NetworkState.UNAVAILABLE) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Turn on internet!",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        )
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}