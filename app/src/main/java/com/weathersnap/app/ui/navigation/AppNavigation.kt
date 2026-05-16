package com.weathersnap.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weathersnap.app.ui.screens.CreateReportScreen
import com.weathersnap.app.ui.screens.CustomCameraScreen
import com.weathersnap.app.ui.screens.SavedReportsScreen
import com.weathersnap.app.ui.screens.WeatherScreen

sealed class Screen(val route: String) {
    object Weather : Screen("weather")
    object CreateReport : Screen("create_report")
    object CustomCamera : Screen("custom_camera")
    object SavedReports : Screen("saved_reports")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Weather.route) {
        composable(Screen.Weather.route) {
            WeatherScreen(
                onNavigateToCreateReport = { navController.navigate(Screen.CreateReport.route) },
                onNavigateToSavedReports = { navController.navigate(Screen.SavedReports.route) }
            )
        }
        composable(Screen.CreateReport.route) {
            CreateReportScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCamera = { navController.navigate(Screen.CustomCamera.route) },
                onReportSaved = {
                    navController.navigate(Screen.SavedReports.route) {
                        popUpTo(Screen.Weather.route)
                    }
                }
            )
        }
        composable(Screen.CustomCamera.route) {
            CustomCameraScreen(
                onPhotoCaptured = { photoPath ->
                    // Pass the photo path back to the previous screen (CreateReport)
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
