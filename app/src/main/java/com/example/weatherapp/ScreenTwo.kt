package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.WeatherAppTheme

class ScreenTwo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}

@Composable
fun MainTemperature(
    city: String,
    temperature: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(16.dp)
    ) {
        Text(
            text = city,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 4f),
                    blurRadius = 4f )
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = temperature,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 4f),
                    blurRadius = 4f )
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun FieldSecondRow(
    hour: String,
    temperature: String,
    weatherState: String,
    modifier: Modifier = Modifier
) {
    val weatherIcon = when (weatherState) {
        "sunny" -> painterResource(R.drawable.sun)
        "cloudy" -> painterResource(R.drawable.cloud)
        "rainy" -> painterResource(R.drawable.rainy)
        else -> painterResource(R.drawable.sun) // Default icon
    }

    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hour,
            color = Color.White,
            fontSize = 16.sp
        )
        Image(
            painter = weatherIcon,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(vertical = 5.dp)
        )
        Text(
            text = temperature,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SecondUIRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .background(Color(0x50000000), RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        FieldSecondRow(
            "15",
            "32°",
            "sunny",
            modifier = Modifier.weight(1f))
        FieldSecondRow(
            "16",
            "31°",
            "cloudy",
            modifier = Modifier.weight(1f))
        FieldSecondRow(
            "17",
            "30°",
            "rainy",
            modifier = Modifier.weight(1f))
        FieldSecondRow(
            "18",
            "29°",
            "sunny",
            modifier = Modifier.weight(1f))
        FieldSecondRow(
            "19",
            "29°",
            "sunny",
            modifier = Modifier.weight(1f))
        FieldSecondRow(
            "20",
            "29°",
            "sunny",
            modifier = Modifier.weight(1f))
    }
}
@Composable
fun FieldThirdRow(
    day: String,
    temperature: String,
    weatherState: String,
    modifier: Modifier = Modifier
) {
    val weatherIcon = when (weatherState) {
        "sunny" -> painterResource(R.drawable.sun)
        "cloudy" -> painterResource(R.drawable.cloud)
        "rainy" -> painterResource(R.drawable.rainy)
        else -> painterResource(R.drawable.sun) // Default icon
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp)
            .background(Color(0x50000000), RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day,
            color = Color.White,
            fontSize = 16.sp
        )
        Image(
            painter = weatherIcon,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Text(
            text = temperature,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ThirdUIRow(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        FieldThirdRow(
            "Mon",
            "32°C",
            "sunny"
        )
        FieldThirdRow(
            "Tue",
            "31°C",
            "cloudy"
        )
        FieldThirdRow(
            "Wed",
            "30°C",
            "rainy"
        )
        FieldThirdRow(
            "Thu",
            "29°C",
            "sunny"
        )
        FieldThirdRow(
            "Fri",
            "28°C",
            "sunny"
        )
        FieldThirdRow(
            "Sun",
            "28°C",
            "sunny"
        )
        FieldThirdRow(
            "Sat",
            "28°C",
            "sunny"
        )
    }
}

@Composable
fun MainLayout(modifier: Modifier = Modifier){
    val background = painterResource(R.drawable.app_templ)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            MainTemperature(
                "Lutsk",
                "29°C"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecondUIRow()
            Spacer(modifier = Modifier.height(16.dp))
            ThirdUIRow()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        MainLayout()
    }
}
