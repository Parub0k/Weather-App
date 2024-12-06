package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.BlueJC
import com.example.weatherapp.ui.theme.PastelBlue
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle


// Основна активність додатку, яка відображає головний екран користувачеві.
// Відповідає за виклик WeatherScreen та управління UI.

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                NavGraph(viewModel = viewModel)
            }
        }
    }
}


@Composable
fun NavGraph(startDestination: String = "weather", viewModel: WeatherViewModel) {
    // Create a navigation controller
    val navController = rememberNavController()

    // Set up the navigation host with the start destination
    NavHost(navController, startDestination = startDestination) {
        // Define the composable for the "weather" screen
        composable("weather") {
            WeatherScreen(navController, viewModel)
        }
        // Define the composable for the "screen_two" screen
        composable("screen_two") {
            ScreenTwoScreen(navController, viewModel)
        }
    }
}

// Detailed Weather screen
@Composable
fun ScreenTwoScreen(navController: NavController?, viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    // Collect the weather data and forecast data state
    val weatherData by viewModel.weatherData.collectAsState()
    val forecastData by viewModel.forecastData.collectAsState()

    // Create a Box layout to fill the screen and center its content
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Choose the appropriate background based on the weather description
        val weatherDescription = weatherData?.weather?.firstOrNull()?.description
        val background = when {
            weatherDescription?.contains("clear sky", ignoreCase = true) == true -> painterResource(R.drawable.sun_3588618_1280)
            weatherDescription?.contains("rain", ignoreCase = true) == true -> painterResource(R.drawable.r__1_)
            weatherDescription?.contains("snow", ignoreCase = true) == true -> painterResource(R.drawable.oip)
            weatherDescription?.contains("mist", ignoreCase = true) == true -> painterResource(R.drawable.oip__1_)
            weatherDescription?.contains("few clouds", ignoreCase = true) == true -> painterResource(R.drawable.r)
            weatherDescription?.contains("scattered clouds", ignoreCase = true) == true -> painterResource(R.drawable.scattered_white_clouds_b19839264934a8d79dd4417668d701ff)
            weatherDescription?.contains("broken clouds", ignoreCase = true) == true -> painterResource(R.drawable.broken_clouds_by_kevintheman_dax9bd4)
            weatherDescription?.contains("broken clouds", ignoreCase = true) == true -> painterResource(R.drawable.broken_clouds_by_kevintheman_dax9bd4)
            weatherDescription?.contains("overcast clouds", ignoreCase = true) == true -> painterResource(R.drawable.broken_clouds_by_kevintheman_dax9bd4)
            else -> painterResource(R.drawable.app_templ)
        }

        // Display the background image
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Create a Column layout to arrange items vertically
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Check if weatherData and forecastData are not null
            weatherData?.let { data ->
                forecastData?.main?.temp?.minus(273.15)?.let { celsiusTemp ->
                    // Display the city name
                    Text(
                        text = data.name,
                        color = Color.White,
                        fontSize = 48.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                    // Add space between text elements
                    Spacer(modifier = Modifier.height(32.dp))
                    // Display the temperature
                    Text(
                        text = "${String.format("%.2f", celsiusTemp)} °C",
                        color = Color.White,
                        fontSize = 86.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                    // Add space between text elements
                    Spacer(modifier = Modifier.height(32.dp))
                    // Display the humidity
                    Text(
                        text = "Humidity: ${data.main.humidity} %",
                        color = Color.White,
                        fontSize = 32.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                    // Add space between text elements
                    Spacer(modifier = Modifier.height(32.dp))
                    // Display the weather description
                    Text(
                        text = data.weather.firstOrNull()?.description.orEmpty(),
                        color = Color.White,
                        fontSize = 32.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            } ?: run {
                // Display loading text if weather data is not available
                Text("Loading weather data...", color = Color.White)
            }

            // Add space between the text and the button
            Spacer(modifier = Modifier.height(32.dp))
            // Display the "Back to Weather" button
            Button(
                onClick = { navController?.navigate("weather") },
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Back to Weather", color = Color.White)
            }
        }
    }
}




//    Головний Composable для відображення екрану пошуку міст.
//    Містить поле введення для введення назви міста та кнопку для виконання пошуку.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(navController: NavController?, viewModel: WeatherViewModel) {
    val cities by viewModel.cities.collectAsState()
    var city by remember { mutableStateOf("") }
    val apiKey = "086ea00f450ae7bcc30164389f96de55"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.app_templ),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(180.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Введіть місто") },
                placeholder = { Text("Введіть місто") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { city = "" },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = BlueJC,
                    focusedIndicatorColor = Color.Black,
                    focusedLabelColor = Color.White
                ),
                trailingIcon = {
                    if (city.isNotEmpty()) {
                        IconButton(onClick = { city = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Очистити текст"
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.findCities(city) },
                colors = ButtonDefaults.buttonColors(BlueJC)
            ) {
                Text(text = "Пошук")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (cities.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    items(cities) { city ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    println("Fetching coordinates for city: ${city.name}")  // Log API call
                                    viewModel.fetchCityCoordinates(city.name, apiKey)
                                    navController?.navigate("screen_two")
                                },
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.elevatedCardElevation(4.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(PastelBlue)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "${city.name}, ${city.country}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Регіон: ${city.region}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }

                }
            }
        }
    }

}

//     Прев'ю компонента WeatherScreen для перегляду в режимі розробки.
