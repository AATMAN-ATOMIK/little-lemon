package com.atomikak.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.atomikak.littlelemon.ui.theme.LittleLemonTheme


@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current

    val firstName = PreferenceHelper.getFirstName(context) ?: "N/A"
    val lastName = PreferenceHelper.getLastName(context) ?: "N/A"
    val email = PreferenceHelper.getEmail(context) ?: "N/A"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp).safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Profile information:", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("First Name: $firstName")
        Text("Last Name: $lastName")
        Text("Email: $email")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                PreferenceHelper.logout(context)
                navController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                }
            }
        ) {
            Text("Log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    // Dummy preview without navController
    // You can skip this in real use or add a dummy NavController for tooling
    LittleLemonTheme {
        ProfileScreen(rememberNavController())
    }
}
