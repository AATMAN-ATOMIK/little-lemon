package com.atomikak.littlelemon

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.atomikak.littlelemon.ui.theme.LittleLemonTheme
import com.atomikak.littlelemon.ui.theme.Primary1
import com.atomikak.littlelemon.ui.theme.Primary2
import com.atomikak.littlelemon.ui.theme.Secondary3
import com.atomikak.littlelemon.ui.theme.Typography
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    val db by lazy { AppDatabase.getDatabase(context = context) }

    val databaseMenuItems by db.menuDao().getAllMenuItems().observeAsState(emptyList())

    val categories = listOf<String>("All", "Starters", "Mains", "Desserts", "Drinks")

    var searchPhrase by remember { mutableStateOf("") }
    var filterCategory by remember { mutableStateOf("All") }

    val filteredItems by remember(searchPhrase, filterCategory, databaseMenuItems) {
        mutableStateOf(
            databaseMenuItems.filter { item ->
                val matchesSearch = searchPhrase.trim().isEmpty() || item.title.contains(searchPhrase, ignoreCase = true)
                val matchesCategory = filterCategory.isEmpty() || filterCategory == "All" || item.category.equals(filterCategory, ignoreCase = true)
                matchesSearch && matchesCategory
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header Row
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(16.dp),
        ) {
            Image(
                modifier = Modifier
                    .height(80.dp)
                    .width(150.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo"
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }

        Box(
            Modifier
                .background(Primary1)
                .padding(16.dp)
                .fillMaxWidth()
                .height(320.dp)
        ) {
            Column {
                // Header
                Text(text = "Little Lemon", style = Typography.displayLarge.copy(color = Primary2))
                // Sub header
                Text(text = "Chicago", style = Typography.displayMedium.copy(color = Color.White))
                // Content row with image
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(top = 12.dp),
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        style =
                            Typography
                                .bodyLarge.copy(color = Color.White, fontWeight = FontWeight.Medium)
                    )

                    Image(
                        modifier = Modifier
                            .width(
                                100.dp
                            )
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop,

                        painter = painterResource(R.drawable.hero_image),
                        contentDescription = "Hero Image"
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 22.dp)
                        .fillMaxWidth(),
                    value = searchPhrase,
                    onValueChange = { value ->
                        searchPhrase = value
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(),
                    singleLine = true,
                    placeholder = { Text(text = "Search", style = Typography.bodySmall) },
                    prefix = { Icon(painter = painterResource(R.drawable.baseline_search_24), contentDescription = "Search Icon") }
                )
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            text = "ORDER FOR DELIVERY!",
            style = Typography.titleLarge
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            categories.forEach {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Secondary3)
                        .padding(8.dp)
                        .clickable(true, onClick = {
                            filterCategory = it
                        })
                )
                {
                    Text(
                        text = it,
                        style = Typography.titleMedium
                    )
                }
            }
        }

        DottedDivider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .height(2.dp)
        )

        MenuItems(menuItemNetworks = filteredItems)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreen(rememberNavController())
    }
}

@Composable
fun MenuItems(menuItemNetworks: List<MenuItemEntity>) {
    if (menuItemNetworks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No items found", textAlign = TextAlign.Center)
        }
    } else {
        menuItemNetworks.map {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(modifier = Modifier.padding(top = 12.dp), text = it.title, style = Typography.titleSmall)
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = it.description,
                            style = Typography.bodyMedium.copy(lineHeight = 20.sp),
                            maxLines = 2,
                            overflow =
                                TextOverflow
                                    .Ellipsis
                        )
                        Text(modifier = Modifier.padding(top = 12.dp), text = "$${it.price}", style = Typography.bodySmall)
                    }

                    GlideImage(
                        imageModel = { it.image },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        loading = { CircularProgressIndicator() },
                        failure = { Text("Image load failed") }
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}

@Composable
fun DottedDivider(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(2.dp),
    color: Color = Color.Black,
    dotSpacing: Float = 10f,
    dotRadius: Float = 2f
) {
    Canvas(modifier = modifier) {
        val width = size.width
        var startX = 0f

        while (startX < width) {
            drawCircle(
                color = color,
                radius = dotRadius,
                center = Offset(x = startX, y = size.height / 2)
            )
            startX += dotSpacing
        }
    }
}