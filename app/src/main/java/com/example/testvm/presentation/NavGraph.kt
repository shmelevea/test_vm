package com.example.testvm.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testvm.presentation.weatherday.DayScreen
import com.example.testvm.presentation.weatherweek.WeekScreen


@Composable
fun TestVMApp() {
    val navController = rememberNavController()
    TestVMAppNavHost(navController = navController)
}

@Composable
fun TestVMAppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "dayScreen") {
        composable("dayScreen") {
            DayScreen(
                onShowForecastClick = { coordinates ->
                    navController.navigate("weekScreen/$coordinates")
                }
            )
        }
        composable(
            route = "weekScreen/{coordinates}",
            arguments = listOf(navArgument("coordinates") { type = NavType.StringType })
        ) { backStackEntry ->
            val coordinates = backStackEntry.arguments?.getString("coordinates") ?: ""
            WeekScreen(
                coordinates = coordinates,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}