package com.app.platemate.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.app.platemate.viewmodel.FoodViewModel
import com.app.platemate.model.FoodListing
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.maps.android.compose.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import java.text.SimpleDateFormat
import java.util.*


class MapScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: FoodViewModel = viewModel()) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(3.0469, 101.7654), 12f) // Balakong, Malaysia
    }
    val coroutineScope = rememberCoroutineScope()

    // Request location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    userLocation = LatLng(location.latitude, location.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation!!, 15f)
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val userLatLng = LatLng(location.latitude, location.longitude)
                viewModel.updateUserLocation(userLatLng)
                cameraPositionState.position = CameraPosition.fromLatLngZoom(userLatLng, 15f)
            }
        }
    }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            placeholder = { Text("Search for a location...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )
        Button(
            onClick = {
                // Implement geocoding logic here to convert searchQuery to LatLng
                coroutineScope.launch {
                    // Example: Move camera to a hardcoded location for demonstration
                    val searchedLocation = LatLng(-33.8688, 151.2093) // Replace with geocoded LatLng
                    cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(searchedLocation, 15f))
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Search")
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            val foodListings = viewModel.foodListings.collectAsState().value
            foodListings.forEach { listing ->
                val position = LatLng(listing.latitude, listing.longitude)
                val distance = userLocation?.let { calculateDistance(it, position) } ?: "N/A"
                Marker(
                    state = rememberMarkerState(position = position),
                    title = "${listing.foodName} - $distance km"
                )
            }
        }
    }
}

fun calculateDistance(start: LatLng, end: LatLng): String {
    val earthRadius = 6371.0 // km
    val dLat = Math.toRadians(end.latitude - start.latitude)
    val dLng = Math.toRadians(end.longitude - start.longitude)
    val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(start.latitude)) * cos(Math.toRadians(end.latitude)) * sin(dLng / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = earthRadius * c
    return String.format("%.2f", distance)
}

@Composable
fun RenderMap(foodListings: List<FoodListing>) {

    val initialPosition = LatLng(-34.0, 151.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 10f)
    }

    GoogleMap(
        cameraPositionState = cameraPositionState
    ) {
        foodListings.forEach { listing ->
            val position = LatLng(listing.latitude, listing.longitude)
            Marker(
                state = rememberMarkerState(position = position),
                title = listing.foodName
            )
        }
    }
}