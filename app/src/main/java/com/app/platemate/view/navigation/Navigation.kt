package com.app.platemate.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.platemate.view.HomeScreen
import com.app.platemate.view.LoginForm
import com.app.platemate.view.SignUpForm
import com.app.platemate.view.Screen
import com.app.platemate.view.AdminScreen

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