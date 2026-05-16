package com.weathersnap.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.weathersnap.app.ui.screens.CreateReportScreen
import com.weathersnap.app.ui.screens.CustomCameraScreen
import com.weathersnap.app.ui.screens.SavedReportsScreen
import com.weathersnap.app.ui.screens.WeatherScreen

sealed class Screen(val route: String) {
    object Weather : Screen("weather")
    object CreateReport : Screen("create_report/{cityName}/{temp}/{condition}/{humidity}/{wind}/{pressure}") {
        fun createRoute(
            cityName: String,
            temp: Float,
            condition: String,
            humidity: Int,
            wind: Float,
            pressure: Float
        ): String {
            // Encode the cityName to handle spaces
            val encodedCity = android.net.Uri.encode(cityName)
            val encodedCondition = android.net.Uri.encode(condition)
            return "create_report/$encodedCity/$temp/$encodedCondition/$humidity/$wind/$pressure"
        }
    }
    object CustomCamera : Screen("custom_camera")
    object SavedReports : Screen("saved_reports")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Weather.route) {
        composable(Screen.Weather.route) {
            WeatherScreen(
                onNavigateToCreateReport = { cityName, temp, condition, humidity, wind, pressure ->
                    navController.navigate(
                        Screen.CreateReport.createRoute(
                            cityName, temp, condition, humidity, wind, pressure
                        )
                    )
                },
                onNavigateToSavedReports = { navController.navigate(Screen.SavedReports.route) }
            )
        }
        composable(
            route = Screen.CreateReport.route,
            arguments = listOf(
                navArgument("cityName") { type = NavType.StringType },
                navArgument("temp") { type = NavType.FloatType },
                navArgument("condition") { type = NavType.StringType },
                navArgument("humidity") { type = NavType.IntType },
                navArgument("wind") { type = NavType.FloatType },
                navArgument("pressure") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val photoPathFromCamera = backStackEntry.savedStateHandle.get<String>("photo_path")

            CreateReportScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCamera = { navController.navigate(Screen.CustomCamera.route) },
                onReportSaved = {
                    navController.navigate(Screen.SavedReports.route) {
                        popUpTo(Screen.Weather.route)
                    }
                },
                cameraPhotoPath = photoPathFromCamera
            )
        }
        composable(Screen.CustomCamera.route) {
            CustomCameraScreen(
                onPhotoCaptured = { photoPath ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("photo_path", photoPath)
                    navController.popBackStack()
                },
                onClose = { navController.popBackStack() }
            )
        }
        composable(Screen.SavedReports.route) {
            SavedReportsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
