package com.app.platemate

// Required imports
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.platemate.ui.theme.LogoColor
import com.app.platemate.ui.theme.TextFieldBackColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Data class for reviews
data class Review(
    val name: String,
    val rating: Int,
    val title: String,
    val review: String,
    val timePosted: String
)

// Sample reviews data
val reviews = listOf(
    Review("Charlie", 5, "Highly Recommended", "Amazing work! Exceeded expectations.", "3 days ago"),
    Review("Charlie", 5, "Highly Recommended", "Amazing work! Exceeded expectations.", "3 days ago"),
    Review("Charlie", 5, "Highly Recommended", "Amazing work! Exceeded expectations.", "3 days ago"),
    Review("Alice", 5, "Great Service", "Excellent work delivered on time!", "2 hours ago"),
    Review("Bob", 4, "Good Experience", "Overall good experience, but a few delays.", "1 day ago"),
    Review("Charlie", 5, "Highly Recommended", "Amazing work! Exceeded expectations.", "3 days ago")
)
//@Preview( showBackground = true, showSystemUi = true)
//@Composable
//fun ProfileRatingScreenPreview(){
//    ProfileRatingScreen("talha","abc@gmail.com",R.drawable.apple)
//}
@Composable
fun ProfileRatingScreen(
    userName: String,
    userEmail: String,
    profileImageRes: Int
) {
    var uName by remember { mutableStateOf(userName) }
    var uEmail by remember { mutableStateOf(userEmail) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        item {
            Image(
                painter = painterResource(id = profileImageRes),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )
        }

        // Editable User Info
        item {
            EditableUserInfo(
                userName = uName,
                userEmail = uEmail,
                onNameChange = { uName = it },
                onEmailChange = { uEmail = it }
            )
        }

        // Update Button
        item {
            Button(
                onClick = { /* Handle update logic */ },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Update")
            }
        }

        // Reviews Section
        items(reviews) { review ->
            ReviewItem(review)
        }
    }
}


@Composable
fun ReviewItem(review: Review) {
    Card(
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Reviewer's Name and Rating
            Text(
                text = "${review.name} - ${review.rating} ★",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // Title
            Text(
                text = review.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Review
            Text(
                text = review.review,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Time Posted
            Text(
                text = review.timePosted,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun EditableUserInfo(
    userName: String,
    userEmail: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    var editableName by remember { mutableStateOf(userName) }
    var editableEmail by remember { mutableStateOf(userEmail) }

    // Editable User Name
    OutlinedTextField(
        value = editableName,
        onValueChange = {
            editableName = it
            onNameChange(it)
        },
        label = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true
    )

    // Editable User Email
    OutlinedTextField(
        value = editableEmail,
        onValueChange = {
            editableEmail = it
            onEmailChange(it)
        },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true
    )
}



// fields



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleField(
    value: String, modifier: Modifier = Modifier,
    onChange: (String) -> Unit, label: String, placeholder: String,
    startIcon: @Composable () -> Unit
){


    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onChange,
        modifier =  modifier,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TextFieldBackColor,  // Set custom background color
            cursorColor = Color.Black,
            errorTextColor = Color.Black,// Set cursor color
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Customize the indicator color when focused
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)  // Unfocused indicator color
        ),

        label = { Text(label) },

//        textStyle = LocalTextStyle.current.copy(color = Color.White),
        leadingIcon = startIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down)}),
        placeholder = { Text(placeholder) },
        singleLine =  true,
        visualTransformation = VisualTransformation.None

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    value: String, modifier: Modifier = Modifier,
    submit: () -> Unit,
    onChange: (String) -> Unit, label: String = "password", placeholder: String = "Enter your password"

){



    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = LogoColor
        )
    }

    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = LogoColor
            )
        }
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TextFieldBackColor,
            cursorColor = Color.Black,
            errorTextColor = Color.Black,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),

        keyboardActions = KeyboardActions(
            onDone = {  //submit()
            }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )



}


