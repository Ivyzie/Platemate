package com.app.platemate.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.platemate.view.navigation.MyNavHost
//import com.app.platemate.navigation.MyNavHost
import com.app.platemate.view.theme.platemateTheme
import com.app.platemate.view.theme.themeColor
import com.app.platemate.view.MapScreen
import com.app.platemate.view.AddFoodListingScreen
import com.app.platemate.viewmodel.FoodViewModel
import com.app.platemate.viewmodel.FoodViewModelFactory
import com.app.platemate.model.FoodRepository
import com.app.platemate.R
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.app.platemate.model.FoodListing
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextField

class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()

            platemateTheme {

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
                0 -> dashBoard(paddingValues)
                1 -> MapScreen()
                2 -> AddFoodListingScreen(viewModel = viewModel(factory = FoodViewModelFactory(FoodRepository())))
                3 -> ProfileRatingScreen(userName = "User Name", profileImageRes = R.drawable.apple)
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dashBoard(paddingValues: PaddingValues, repository: FoodRepository = FoodRepository()) {
    val viewModel: FoodViewModel = viewModel(factory = FoodViewModelFactory(repository))
    val LogoColor = themeColor
    var searchingValue by remember { mutableStateOf("") }
    val foodListings = viewModel.foodListings.collectAsState().value
    val context = LocalContext.current

    // Filter the food listings based on the search query
    val filteredFoodListings = foodListings.filter { listing ->
        listing.foodName.contains(searchingValue, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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

        // LazyColumn for the Filtered Food Items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredFoodListings) { listing ->
                FoodItem(
                    foodImage = painterResource(id = R.drawable.hamburger), // Replace with your image logic
                    foodName = listing.foodName,
                    distance = listing.distance,
                    contact = listing.contactInfo,
                    postedTime = listing.postedTime,
                    onEditClick = {
                        // Implement edit logic here
                    },
                    onDeleteClick = {
                        viewModel.deleteFoodListing(listing.id)
                        Toast.makeText(context, "Food listing deleted", Toast.LENGTH_SHORT).show()
                    },

                )
            }
        }
    }
}

@Composable
fun FoodItem(
    foodImage: Painter,
    foodName: String,
    distance: String,
    contact: String,
    postedTime: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
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
                Text("$foodName", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Distance: $distance km", style = MaterialTheme.typography.labelMedium)
                Text("Contact: $contact", style = MaterialTheme.typography.labelMedium)
                Text("Posted: $postedTime", style = MaterialTheme.typography.labelMedium)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onEditClick, // Use the onEditClick callback
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Edit", style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                    Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete", style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                }
            }
        }
    }
}

@Composable
fun EditFoodListingDialog(
    foodListing: FoodListing,
    onDismiss: () -> Unit,
    onSave: (FoodListing) -> Unit
) {
    var foodName by remember { mutableStateOf(foodListing.foodName) }
    var distance by remember { mutableStateOf(foodListing.distance) }
    var contactInfo by remember { mutableStateOf(foodListing.contactInfo) }
    var postedTime by remember { mutableStateOf(foodListing.postedTime) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Edit Food Listing") },
        text = {
            Column {
                TextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name") }
                )
                TextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("Distance") }
                )
                TextField(
                    value = contactInfo,
                    onValueChange = { contactInfo = it },
                    label = { Text("Contact Info") }
                )
                TextField(
                    value = postedTime,
                    onValueChange = { postedTime = it },
                    label = { Text("Posted Time") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(foodListing.copy(
                    foodName = foodName,
                    distance = distance,
                    contactInfo = contactInfo,
                    postedTime = postedTime
                ))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}


