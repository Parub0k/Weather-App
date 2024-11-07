package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.BlueJC
import com.example.weatherapp.ui.theme.DarkBlueJC
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(){
    val viewModel: WeatherViewModel = viewModel()
    // val weatherData by viewModel.weatherData.collectAsState()
    val cities by viewModel.cities.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
        val apiKey = "086ea00f450ae7bcc30164389f96de55"

        Box(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.app_templ),
                contentScale = ContentScale.FillBounds
            )) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(180.dp))
                    OutlinedTextField(value = city,
                        onValueChange = {city = it},
                        label = { Text("Введіть місто") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = BlueJC,
                            focusedIndicatorColor = BlueJC,
                            focusedLabelColor = DarkBlueJC
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.findCities(city) },
                        colors = ButtonDefaults.buttonColors(BlueJC)
                    ) {
                        Text(text = "Пошук")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                            // Список міст
                    if (cities.isNotEmpty()) {
                        LazyColumn {
                            items(cities) { city ->
                                Text(
                                    text = "${city.name}, ${city.country}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                            // Виклик запиту погоди для обраного міста
                                            viewModel.fetchWeather(city.name, apiKey)
                                            // Тут відбудеться перехід на інший екран
                                        },
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
        }
}


@Preview(showBackground = true)
@Composable
fun WeatherPreview(){
        WeatherScreen()
}