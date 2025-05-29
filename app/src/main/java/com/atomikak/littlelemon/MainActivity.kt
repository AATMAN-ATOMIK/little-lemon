package com.atomikak.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.atomikak.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private val db by lazy {
        AppDatabase.getDatabase(this)
    }

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LittleLemonTheme {
                MainContent()
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (db.menuDao().isEmpty()) {
                val menuItems = fetchMenu()
                saveMenuToDatabase(menuItems)
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemEntity> {
        val dao = db.menuDao()
        val response: MenuNetwork = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()
        val menuItems = response.menu.map { it.toEntity() }
        return menuItems
    }

    private fun saveMenuToDatabase(menuItemEntity: List<MenuItemEntity>) {
        db.menuDao().insertMenuItems(menuItemEntity)
    }
}

@Composable
fun MainContent() {
    val context = LocalContext.current
    val navController = rememberNavController()

    // Read the preference once at composition
    val isLoggedIn by remember {
        androidx.compose.runtime.mutableStateOf(
            PreferenceHelper.isLoggedIn(context)
        )
    }

    val startDestination = if (isLoggedIn) Home.route else Onboarding.route

    MyNavigation(navController = navController, startDestination = startDestination)
}