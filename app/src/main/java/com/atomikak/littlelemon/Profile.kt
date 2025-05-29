package com.atomikak.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.atomikak.littlelemon.ui.theme.LittleLemonTheme
import com.atomikak.littlelemon.ui.theme.Primary1
import com.atomikak.littlelemon.ui.theme.Primary2
import com.atomikak.littlelemon.ui.theme.Secondary3


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current

    val firstName = PreferenceHelper.getFirstName(context) ?: "N/A"
    val lastName = PreferenceHelper.getLastName(context) ?: "N/A"
    val email = PreferenceHelper.getEmail(context) ?: "N/A"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Navigate Back",
                        modifier = Modifier.clickable(
                            onClick = {
                                navController
                                    .popBackStack()
                            },
                        ),
                    )
                }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Profile Information",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Primary1
                    )

                    ProfileItem(icon = Icons.Default.Person, label = "First Name", value = firstName)
                    ProfileItem(icon = Icons.Default.Person, label = "Last Name", value = lastName)
                    ProfileItem(icon = Icons.Default.Email, label = "Email", value = email)
                }
            }
        }

        // Button fixed to the bottom
        Button(
            onClick = {
                PreferenceHelper.logout(context)
                navController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                }
            },
            colors = ButtonColors(
                containerColor = Primary2,
                contentColor = Color.Black,
                disabledContainerColor = Secondary3,
                disabledContentColor = Color.Black
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Log out")
        }
    }
}

@Composable
fun ProfileItem(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            modifier = Modifier.size(24.dp),
            tint = Primary1
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Primary1)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        ProfileScreen(rememberNavController())
    }
}
