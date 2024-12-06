package com.app.foodwaste

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.foodwaste.navigation.MyNavHost
//import com.app.foodwaste.navigation.MyNavHost
import com.app.foodwaste.ui.theme.FoodwasteTheme
import com.app.foodwaste.ui.theme.themeColor

class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            navController = rememberNavController()

            FoodwasteTheme {

            MyNavHost(navController = navController)
//                HomeScreen()
            }
        }
    }

}

//    @Preview(showBackground = true)
//    @Composable
//    fun AddFoodScreenPreview() {
//        AddFoodScreen(context =  this)
//    }



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val LogoColor = themeColor

    // Observe the current route to change selected tab
    var selectedTab by remember { mutableStateOf(0) }


    Scaffold(
        modifier =  Modifier.fillMaxSize(),
//        topBar = {
//
//
//        },
        bottomBar = {
            NavigationBar(
                containerColor = LogoColor
            ) {
                NavigationBarItem(
                        selected = selectedTab == 0,
                    onClick = {
//                        navController.navigate(Screen.HomeScreen.route)
                        selectedTab = 0},
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {

                        selectedTab = 1
                    },
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    label = { Text("Search") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {

                        selectedTab = 2
                    },
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Orders") },
                    label = { Text("Orders") }
                )
                NavigationBarItem(
                    selected =   selectedTab == 3,
                    onClick = {
//                        navController.navigate(Screen.ProfileScreen.route)
                        selectedTab = 3
                    },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        },

        content = { paddingValues ->

            when(selectedTab){
                0-> dashBoard(paddingValues)
                3 -> ProfileRatingScreen("talha","abc@gmail.com", R.drawable.apple)
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dashBoard(paddingValues: PaddingValues) {
    val LogoColor = themeColor
    var searchingValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Apply padding from Scaffold
    ) {
        // Title and Search Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(32.dp))
            Text(
                "Food Management",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(18.dp))
            TextField(
                value = searchingValue,
                onValueChange = { searchingValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 10.dp),
                placeholder = { Text("Search for food...") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = LogoColor,
                    focusedIndicatorColor = LogoColor,
                    unfocusedIndicatorColor = LogoColor.copy(alpha = 0.5f)
                ),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // LazyColumn for the Food Items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp) // Optional additional padding
        ) {
            items(3) {
                FoodItem(
                    foodImage = painterResource(id = R.drawable.hamburger), // Replace with your image
                    distance = "5 km away",
                    contact = "+123456789",
                    postedTime = "2 hours ago"
                )
            }
        }
    }
}


@Composable
fun FoodItem(
    foodImage: Painter,
    distance: String,
    contact: String,
    postedTime: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = foodImage,
                contentDescription = "Food image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {

                Text("Food Item Name", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Distance: $distance", style = MaterialTheme.typography.labelMedium)
                Text("Contact: $contact", style = MaterialTheme.typography.labelMedium)
                Text("Posted: $postedTime", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}



