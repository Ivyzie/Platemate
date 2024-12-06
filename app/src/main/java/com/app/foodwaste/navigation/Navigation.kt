package com.app.platemate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.platemate.HomeScreen
import com.app.platemate.LoginForm
import com.app.platemate.SignUpForm
import com.app.platemate.models.Screen

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