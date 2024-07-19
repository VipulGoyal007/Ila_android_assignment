package com.example.testapplication.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testapplication.screens.CategoryScreen
import com.example.testapplication.screens.DetailScreen

@Composable
fun Navigation(listData: List<String>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationScreens.CategoryScreen.route) {
        composable(route = NavigationScreens.CategoryScreen.route) {
            CategoryScreen(listData = listData, navController = navController)
        }

        composable(route = NavigationScreens.DetailScreen.route+"/{email}", arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
                defaultValue="test@mailinator.com"
                nullable=true
            }
        )) {
            val email = it.arguments?.getString("email") ?: ""
            DetailScreen(listData = listData, email= "")
        }

    }
}
