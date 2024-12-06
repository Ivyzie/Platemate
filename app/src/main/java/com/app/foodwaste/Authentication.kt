package com.app.platemate

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.platemate.models.Screen
import com.app.platemate.ui.theme.LogoColor


@Preview
@Composable
fun SignUpFormPreview(){
    val mockNavController = rememberNavController()
    SignUpForm( mockNavController)
}

@Composable
fun SignUpForm(navController: NavHostController) {

    var username by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var confirmPassword by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}

    Surface {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp).verticalScroll(
                    enabled = true,
                    state = rememberScrollState(),
                    flingBehavior = flingBehavior(),
                    reverseScrolling = true
                ),

        ) {


            Image(painter = painterResource(R.drawable.apple)
                , contentDescription = "LoginImage",
                modifier = Modifier.size(250.dp))
//            Spacer()
            Spacer(Modifier.height(18.dp))


            Text("Sign Up",
                style = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Normal)
            )
            Spacer(Modifier.height(16.dp))
            Text("Share Food",
                style = TextStyle(color = Color.Black, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(16.dp))

            // fields section here
            val leadingIconEmail = @Composable {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "email",
                    tint = LogoColor
                )
            }
            val leadingIconUserName = @Composable {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "person",
                    tint = LogoColor
                )
            }

            SimpleField(
                onChange = { username = it }, value = username, modifier = Modifier.fillMaxWidth(),
                label ="Username", placeholder = "Enter your username"
                , startIcon = leadingIconUserName)
            Spacer(Modifier.height(16.dp))

            SimpleField(
                onChange = {}, value = email, modifier = Modifier.fillMaxWidth(),
                label ="Email", placeholder = "Enter your email"
                , startIcon = leadingIconEmail )

            Spacer(Modifier.height(16.dp))

            PasswordField(
                value = password,
                onChange = { password = it },
                submit = { },
                label = "Password",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            PasswordField(
                value = confirmPassword,
                onChange = { confirmPassword = it },
                label = "Confirm password",
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {

                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LogoColor,
                    contentColor = Color.White
                )

            ) {
                Text("Sign Up")
            }
            Spacer(Modifier.height(16.dp))
            Row{
                Text("Old user?",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Login",
                    style = TextStyle(
                        color = LogoColor, // Use your logo color
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable(onClick = {  navController.navigate(Screen.Login.route) }) // Make text clickable
                )

            }

        }
    }
}



@Preview
@Composable
fun LoginFormPreview(){
    val mockNavController = rememberNavController()
    LoginForm(mockNavController)
}

@Composable
fun LoginForm(navController: NavHostController) {

    Surface {


        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp).verticalScroll(
                    enabled = true,
                    state = rememberScrollState(),
                    flingBehavior = flingBehavior(),
                    reverseScrolling = true
                )
        ) {


            Image(painter = painterResource(R.drawable.hamburger)
                , contentDescription = "LoginImage",
                modifier = Modifier.size(250.dp))
            Spacer(Modifier.height(18.dp))

            val leadingIcon = @Composable {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "",
                    tint = LogoColor
                )
            }
            Text("Login",
                style = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Normal)
            )
            Spacer(Modifier.height(16.dp))
            Text("Welcome Back",
                style = TextStyle(color = Color.Black, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(16.dp))

            SimpleField(
                onChange = { email = it }, value = email, modifier = Modifier.fillMaxWidth(),
                label ="email", placeholder = "Enter your email"
                , startIcon = leadingIcon)

            Spacer(Modifier.height(16.dp))

            PasswordField(
                value = password,
                onChange = { password = it },
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.HomeScreen.route)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LogoColor,
                    contentColor = Color.White
                )

            ) {
                Text("Login")
            }
            Spacer(Modifier.height(16.dp))
            Row{
                Text("New user?",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "SignUp",
                    style = TextStyle(
                        color = LogoColor, // Use your logo color
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable(onClick = {  navController.navigate(Screen.SignUp.route) }) // Make text clickable
                )

            }

        }
    }
}
