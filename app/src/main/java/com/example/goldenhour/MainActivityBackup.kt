//package com.example.goldenhour
//
//import android.graphics.Paint.Align
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.goldenhour.ui.theme.GoldenHourTheme
//import kotlin.math.acos
//import kotlin.math.asin
//import kotlin.math.atan
//import kotlin.math.cos
//import kotlin.math.floor
//import kotlin.math.sin
//import kotlin.math.tan
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            GoldenHourTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                ) {
//                    GoldenHourApp()
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GoldenHourApp() {
//
//    val viewModel: LatLongViewModel = viewModel()
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(80.dp)
//    ) {
//        PowerButton(
//            modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center)
//        )
//        LatLongScreen(
//            viewModel = viewModel
//        )
//    }
//}
//
//@Composable
//fun PowerButton(
//    modifier: Modifier = Modifier
//) {
//    var isOn by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(R.drawable.sun),
//            contentDescription = "sun",
//            modifier = Modifier.size(200.dp)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = {
//                isOn = !isOn
//            },
//            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
//        ) {
//            Text(if (isOn) "On" else "Off")
//        }
//
//    }
//}
//
//class LatLongViewModel : ViewModel() {
//    fun storeLatLong(latitude: String, longitude: String) {
//        // store or process latitude and longitude here
//    }
//}
//
//@Composable
//fun LatLongScreen(viewModel: LatLongViewModel) {
//    var lat by remember { mutableStateOf("") }
//    var long by remember { mutableStateOf("") }
//
//    LatLongFields(
//        value1 = lat,
//        value2 = long,
//        onValueChange1 = { lat = it },
//        onValueChange2 = { long = it },
//        onSubmit = { latitude, longitude ->
//            viewModel.storeLatLong(latitude, longitude)
//        }
//    )
//}
//
//@Composable
//fun LatLongFields(
//    value1: String,
//    value2: String,
//    onValueChange1: (String) -> Unit,
//    onValueChange2: (String) -> Unit,
//    onSubmit: (String, String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            modifier = modifier,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            OutlinedTextField(
//                value = value1,
//                onValueChange = onValueChange1,
//                label = { Text("Latitude") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            OutlinedTextField(
//                value = value2,
//                onValueChange = onValueChange2,
//                label = { Text("Longitude") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = { onSubmit(value1, value2) }) {
//                Text("Submit")
//            }
//        }
//    }
//}
//
//
//fun sunsetAlgorithm(latitude: Double, longitude: Double): Double {
//
//    /* Straight from here
//     * https://web.archive.org/web/20161202180207/http://williams.best.vwh.net/sunrise_sunset_algorithm.htm
//     */
//
//
//    val day = 9
//    val month = 10
//    val year = 2024
//    val latitude = latitude
//    val longitude = longitude
//    val zenith = 90.0
//
//    val N1 = floor(275.0 * month / 9).toInt()
//    val N2 = floor((month + 9) / 12.0).toInt()
//    val N3 = (1 + floor((year - 4 * floor(year / 4.0) + 2) / 3.0)).toInt()
//    val N = N1 - (N2 * N3) + day - 30
//
//    val lngHour = longitude / 15
//    val t = N + ((18 - lngHour) / 24.0)
//
//    val M = (0.9856 * t) - 3.289
//
//    var L = (M + (1.916 * sin(Math.toRadians(M))) + (0.020 * sin(Math.toRadians(2 * M))) + 282.634) % 360
//    L = if (L < 0) L + 360 else L
//
//    var RA = (Math.toDegrees(atan(0.91764 * tan(Math.toRadians(L))))) % 360
//    RA = if (RA < 0) RA + 360 else RA
//
//    val Lquadrant = (floor(L / 90)).toInt() * 90
//    val RAquadrant = (floor(RA / 90)).toInt() * 90
//    RA += (Lquadrant - RAquadrant)
//
//    RA /= 15
//
//    val sinDec = 0.39782 * sin(Math.toRadians(L))
//    val cosDec = cos(Math.toRadians(Math.toDegrees(asin(sinDec))))
//
//    val cosH = (cos(Math.toRadians(zenith)) - (sinDec * sin(Math.toRadians(latitude)))) / (cosDec * cos(Math.toRadians(latitude)))
//
//    var H = Math.toDegrees(acos(cosH))
//
//    H /= 15
//
//    val T = H + RA - (0.06571 * t) - 6.622
//
//    var UT = (T - lngHour) % 24
//    // But why kotlin
//    UT = if (UT < 0) UT + 24 else UT
//
//    val localT = UT - 4
//
//    return localT
//}
