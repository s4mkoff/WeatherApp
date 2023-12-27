package com.s4mkoff.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s4mkoff.weatherapp.R

@Composable
fun WeatherCard(
    imageId: Int,
    date: String,
    maxTemperature: String,
    minTemperature: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .size(
                height = 101.dp,
                width = 95.dp
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .padding(bottom = 6.dp, top = 16.dp)
                    .size(24.dp)
            )
            Text(
                text = date,
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF444444),
                modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$maxTemperature°C",
                        fontSize = 8.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF999999),
                        letterSpacing = 0.8.sp,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.temperature_up),
                        contentDescription = "Temperature Max",
                        modifier = Modifier.padding(start=1.dp, end = 3.dp),
                        tint = Color(0xFFAAAAAA)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$minTemperature°C",
                        fontSize = 8.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF999999),
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.temperature_down),
                        contentDescription = "Temperature Max",
                        modifier = Modifier.padding(start=1.dp),
                        tint = Color(0xFFAAAAAA)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherCardPrev() {
    WeatherCard(1, "", "", "")
}