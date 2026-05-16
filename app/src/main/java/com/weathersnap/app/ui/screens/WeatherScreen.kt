package com.weathersnap.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.weathersnap.app.data.remote.models.CityDto
import com.weathersnap.app.ui.theme.*
import com.weathersnap.app.ui.viewmodel.WeatherUiState
import com.weathersnap.app.ui.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    onNavigateToCreateReport: (String, Float, String, Int, Float, Float) -> Unit,
    onNavigateToSavedReports: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(PrimaryLightGreen)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "WeatherSnap",
                    color = TextOnPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Live weather reports with camera evidence",
                    color = TextOnPrimary.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
            Button(
                onClick = onNavigateToSavedReports,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Reports", color = PrimaryLightGreen, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceDark)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    label = { Text("City", color = TextSecondary) },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryLightGreen,
                        unfocusedBorderColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { /* Auto search */ },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightGreen),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Search", color = TextOnPrimary)
                }
            }
            Text(
                text = "Enter more than 2 letters to start city suggestions.",
                color = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Suggestions Dropdown-like
            if (suggestions.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceVariantDark)
                        .heightIn(max = 200.dp)
                ) {
                    items(suggestions) { city ->
                        Text(
                            text = "${city.name}, ${city.country ?: ""}",
                            color = TextPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectCity(city) }
                                .padding(12.dp)
                        )
                        HorizontalDivider(color = SurfaceDark)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Details Section
        if (weatherState.isLoading) {
            CircularProgressIndicator(color = PrimaryLightGreen, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (weatherState.error != null) {
            Text(text = weatherState.error ?: "", color = MaterialTheme.colorScheme.error)
        } else if (weatherState.weather != null && weatherState.cityName != null) {
            val weather = weatherState.weather!!
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = weatherState.cityName!!,
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = viewModel.mapWeatherCode(weather.weatherCode),
                            color = TextSecondary,
                            fontSize = 16.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryLightGreen.copy(alpha = 0.2f))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "${weather.temperature}°C",
                            color = PrimaryLightGreen,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WeatherStatCard("Humidity", "${weather.humidity}%", WeatherGreen, Modifier.weight(1f))
                    WeatherStatCard("Wind", "${weather.windSpeed} m/s", WeatherBlue, Modifier.weight(1f))
                    WeatherStatCard("Pressure", "${weather.pressure}", WeatherOrange, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceVariantDark)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Report readiness", color = TextSecondary, fontSize = 14.sp)
                    Text("Camera and Room DB enabled", color = TextPrimary, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onNavigateToCreateReport(
                            weatherState.cityName!!,
                            weather.temperature.toFloat(),
                            viewModel.mapWeatherCode(weather.weatherCode),
                            weather.humidity,
                            weather.windSpeed.toFloat(),
                            weather.pressure.toFloat()
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightGreen),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Report", color = TextOnPrimary)
                }
            }
        }
    }
}

@Composable
fun WeatherStatCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceVariantDark)
            .padding(12.dp)
    ) {
        Text(title, color = TextSecondary, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, color = color, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}
