package com.atomikak.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.atomikak.littlelemon.ui.theme.LittleLemonTheme
import com.atomikak.littlelemon.ui.theme.Primary1
import com.atomikak.littlelemon.ui.theme.Primary2
import com.atomikak.littlelemon.ui.theme.Secondary3
import com.atomikak.littlelemon.ui.theme.Typography
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            // Little Lemon Logo
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(80.dp)
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Little Lemon Logo"
                )
            }

            Column {
                // Title
                Text(
                    modifier = Modifier
                        .background(color = Primary1)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 42.dp),
                    text = "Let's get to know you",
                    textAlign = TextAlign.Center,
                    style = Typography.headlineMedium.copy(color = Color.White),
                )


                Text(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp), text = "Personal information", style = Typography.titleLarge)

                OutlinedTextField(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    textStyle = Typography.bodyMedium,
                    value = firstName,
                    onValueChange = { value -> firstName = value },
                    label = { Text(text = "First name") },
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    value = lastName,
                    textStyle = Typography.bodyMedium,
                    onValueChange = { value -> lastName = value },
                    label = { Text(text = "Last name") },
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    value = email,
                    textStyle = Typography.bodyMedium,
                    onValueChange = { value -> email = value },
                    label = { Text(text = "Email") },
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                        PreferenceHelper.saveLoginDetails(context, firstName, lastName, email)
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Home.route)
                    } else {
                        Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonColors(
                    containerColor = Primary2,
                    contentColor = Color.Black,
                    disabledContainerColor = Secondary3,
                    disabledContentColor = Color.Black
                ),

                ) {
                Text(text = "Register")
            }

        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding(
            navController = NavHostController(LocalContext.current)
        )
    }
}