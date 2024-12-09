package com.app.platemate.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.platemate.viewmodel.FoodViewModel
import com.app.platemate.model.FoodListing
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddFoodListingScreen(viewModel: FoodViewModel = viewModel()) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val userLocation by viewModel.userLocation.collectAsState()
    var foodName by remember { mutableStateOf("") }
    var contactInfo by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }
    val postedTime = remember { getCurrentTime() }

    // Request location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    viewModel.updateUserLocation(LatLng(location.latitude, location.longitude))
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                viewModel.updateUserLocation(LatLng(location.latitude, location.longitude))
            }
        }
    }

    // UI for adding food listing
    Column {
        TextField(
            value = foodName,
            onValueChange = { foodName = it },
            label = { Text("Food Name") }
        )
        TextField(
            value = contactInfo,
            onValueChange = { contactInfo = it },
            label = { Text("Contact Info") }
        )
        Button(onClick = {
            val distance = userLocation?.let { calculateDistance(it, LatLng(latitude, longitude)) } ?: "N/A"
            viewModel.addFoodListing(
                FoodListing(
                    foodName = foodName,
                    distance = distance,
                    contactInfo = contactInfo,
                    postedTime = postedTime,
                    latitude = latitude.toDouble(), // Convert to String if needed
                    longitude = longitude.toDouble() // Convert to String if needed
                )
            )
        }) {
            Text("Add Listing")
        }
    }
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}