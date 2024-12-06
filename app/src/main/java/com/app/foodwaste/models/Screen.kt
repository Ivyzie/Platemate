package com.app.foodwaste.models

// Sealed class for defining routes
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object HomeScreen : Screen("homeScreen")

}
