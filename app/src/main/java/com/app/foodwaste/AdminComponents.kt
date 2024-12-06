package com.app.platemate

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.platemate.ui.theme.themeColor


@Composable
fun AddFoodScreen(context : Context) {
    var foodName by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var contactInfo by remember { mutableStateOf("") }
    var postedTime by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launchers for gallery and camera
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                Toast.makeText(context, "Image captured!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Food Name Input
        OutlinedTextField(
            value = foodName,
            onValueChange = { foodName = it },
            label = { Text("Food Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Distance Input
        OutlinedTextField(
            value = distance,
            onValueChange = { distance = it },
            label = { Text("Distance (in km)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Contact Info Input
        OutlinedTextField(
            value = contactInfo,
            onValueChange = { contactInfo = it },
            label = { Text("Contact Info") },
            modifier = Modifier.fillMaxWidth()
        )

        // Image Picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Pick from Gallery")
            }

            Button(onClick = { cameraLauncher.launch(null) }) {
                Text("Capture with Camera")
            }
        }

        // Display Picked Image
        imageUri?.let {
            AsyncImage(
                model = it,
                contentDescription = "Food Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        } ?: Text(
            text = "No image selected",
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        // Add Food Button
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Food Added: $foodName",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Food")
        }
    }
}


@Composable
fun FoodItemAdmin(
    foodImage: Painter,
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
            // Food Image
            Image(
                painter = foodImage,
                contentDescription = "Food image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Food Details
            Column(modifier = Modifier.weight(1f)) {
                Text("Food Item Name", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Distance: $distance", style = MaterialTheme.typography.labelMedium)
                Text("Contact: $contact", style = MaterialTheme.typography.labelMedium)
                Text("Posted: $postedTime", style = MaterialTheme.typography.labelMedium)
                // Action Buttons
                Row(
                    horizontalArrangement  = Arrangement.spacedBy(8.dp)
//                    verticalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = onEditClick,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
//                        modifier = Modifier
//                            .height(32.dp)
//                            .width(72.dp)
                    ) {
                        Text("Edit", style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                    Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
//                        modifier = Modifier
//                            .height(32.dp)
//                            .width(72.dp)
                    ) {
                        Text("Delete", style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                }
            }




        }
        Spacer(modifier = Modifier.width(16.dp))

    }
}

@Preview( showBackground = true, showSystemUi = true)
@Composable
fun adminScreenPreview(){
    adminScreen()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun adminScreen(){
    val LogoColor = themeColor
    var searchingValue by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Text("Admin Dashboard"
            ,
            style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(18.dp))
        // Search bar
        TextField(
            value = searchingValue ,
            onValueChange = { searchingValue  = it},
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
        Spacer(Modifier.height(18.dp))
        FloatingActionButton(
            onClick = {
            },
        ) {
            Icon(Icons.Filled.Add, "Floating action button to add food.")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            items(3) {
                FoodItemAdmin(
                    foodImage = painterResource(id = R.drawable.hamburger), // Replace with your image
                    distance = "5 km away",
                    contact = "+123456789",
                    postedTime = "2 hours ago",
                    onEditClick = {

                    },
                    onDeleteClick = {

                    }
                )
            }
        }
    }
}
