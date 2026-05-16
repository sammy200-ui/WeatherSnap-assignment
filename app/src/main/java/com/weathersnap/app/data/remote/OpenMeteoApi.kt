package com.weathersnap.app.data.remote

import com.weathersnap.app.data.remote.models.GeocodingResponse
import com.weathersnap.app.data.remote.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface OpenMeteoApi {

    @GET
    suspend fun searchCity(
        @Url url: String = "https://geocoding-api.open-meteo.com/v1/search",
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): GeocodingResponse

    @GET
    suspend fun getCurrentWeather(
        @Url url: String = "https://api.open-meteo.com/v1/forecast",
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m,surface_pressure"
    ): WeatherResponse
}
