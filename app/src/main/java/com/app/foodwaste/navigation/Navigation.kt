package com.app.foodwaste.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.foodwaste.HomeScreen
import com.app.foodwaste.LoginForm
import com.app.foodwaste.SignUpForm
import com.app.foodwaste.models.Screen

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginForm(navController)
        }

        composable(Screen.SignUp.route) {
            SignUpForm(navController)
        }


        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
    }
}