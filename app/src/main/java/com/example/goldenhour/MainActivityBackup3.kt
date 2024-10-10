//package com.example.goldenhour
//
//import android.Manifest
//import android.app.AlarmManager
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.content.pm.PackageManager
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.core.content.ContextCompat
//import com.example.goldenhour.ui.theme.GoldenHourTheme
//import java.util.Calendar
//import kotlin.math.acos
//import kotlin.math.asin
//import kotlin.math.atan
//import kotlin.math.cos
//import kotlin.math.floor
//import kotlin.math.roundToInt
//import kotlin.math.sin
//import kotlin.math.tan
//
//class MainActivityBackup3 : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        createNotificationChannel()
//
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
//
//    val CHANNEL_ID = "sunset_channel"
//
//    fun createNotificationChannel() {
//        val name = "High Importance Channel"
//        val descriptionText = "Can't get away from me :)"
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun GoldenHourApp() {
//        val context = LocalContext.current
//        val sharedPreferences: SharedPreferences = context.getSharedPreferences("locationPrefs", Context.MODE_PRIVATE)
//
//        var latitude by remember { mutableStateOf(sharedPreferences.getString("latitude", "") ?: "") }
//        var longitude by remember { mutableStateOf(sharedPreferences.getString("longitude", "") ?: "") }
//
//        val notificationPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//            } else {
//            }
//        }
//
//        LaunchedEffect(Unit) {
//            when {
//                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
//                }
//                else -> {
//                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//        }
//
//        fun checkNotificationPermission() {
//            when {
//                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
//                }
//                else -> {
//                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//        }
//
//        fun showTimeNotification(context: Context, title: String, message: String) {
//            checkNotificationPermission()
//
//            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.sun)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//
//            val notificationManager = NotificationManagerCompat.from(context)
//            notificationManager.notify(786, builder.build())
//        }
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.Center)
//        ) {
//            PowerButton(
//                modifier = Modifier
//                    .wrapContentSize(Alignment.Center)
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                OutlinedTextField(
//                    value = latitude,
//                    onValueChange = { latitude = it },
//                    label = { Text("Latitude") },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(8.dp)
//                )
//                OutlinedTextField(
//                    value = longitude,
//                    onValueChange = { longitude = it },
//                    label = { Text("Longitude") },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(8.dp)
//                )
//            }
//            Button(
//                onClick = {
//                    with(sharedPreferences.edit()) {
//                        putString("latitude", latitude)
//                        putString("longitude", longitude)
//                        apply()
//                    }
//
//                    val lat = latitude.toDoubleOrNull()
//                    val lon = longitude.toDoubleOrNull()
//
//                    if (lat != null && lon != null) {
//                        var time = sunsetAlgorithm(lat, lon)
//                        var goldenHour = time - 1
//                        var hour = goldenHour.toInt()
//                        var second = ((goldenHour - hour) * 60).roundToInt()
//                        showTimeNotification(context, "Golden Hour Today: $hour:$second", "Latitude: $latitude, Longitude: $longitude")
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
//                modifier = Modifier
//                    .padding(8.dp)
//            ) {
//                Text("Save")
//            }
//        }
//    }
//
//    @Composable
//    fun PowerButton(
//        modifier: Modifier = Modifier
//    ) {
//        var isOn by remember { mutableStateOf(false) }
//
//        Column(
//            modifier = modifier,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(R.drawable.sun),
//                contentDescription = "sun",
//                modifier = Modifier.size(200.dp)
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    isOn = !isOn
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
//            ) {
//                Text(if (isOn) "On" else "Off")
//            }
//
//        }
//    }
//
//    fun sunsetAlgorithm(latitude: Double, longitude: Double): Double {
//
//        /* Straight from here
//         * https://web.archive.org/web/20161202180207/http://williams.best.vwh.net/sunrise_sunset_algorithm.htm
//         */
//
//        val day = 9
//        val month = 10
//        val year = 2024
//        val latitude = latitude
//        val longitude = longitude
//        val zenith = 90.0
//
//        val N1 = floor(275.0 * month / 9).toInt()
//        val N2 = floor((month + 9) / 12.0).toInt()
//        val N3 = (1 + floor((year - 4 * floor(year / 4.0) + 2) / 3.0)).toInt()
//        val N = N1 - (N2 * N3) + day - 30
//        val lngHour = longitude / 15
//        val t = N + ((18 - lngHour) / 24.0)
//        val M = (0.9856 * t) - 3.289
//        var L = (M + (1.916 * sin(Math.toRadians(M))) + (0.020 * sin(Math.toRadians(2 * M))) + 282.634) % 360
//        L = if (L < 0) L + 360 else L
//        var RA = (Math.toDegrees(atan(0.91764 * tan(Math.toRadians(L))))) % 360
//        RA = if (RA < 0) RA + 360 else RA
//        val Lquadrant = (floor(L / 90)).toInt() * 90
//        val RAquadrant = (floor(RA / 90)).toInt() * 90
//        RA += (Lquadrant - RAquadrant)
//        RA /= 15
//        val sinDec = 0.39782 * sin(Math.toRadians(L))
//        val cosDec = cos(Math.toRadians(Math.toDegrees(asin(sinDec))))
//        val cosH = (cos(Math.toRadians(zenith)) - (sinDec * sin(Math.toRadians(latitude)))) / (cosDec * cos(Math.toRadians(latitude)))
//        var H = Math.toDegrees(acos(cosH))
//        H /= 15
//        val T = H + RA - (0.06571 * t) - 6.622
//        var UT = (T - lngHour) % 24
//        // But why kotlin
//        UT = if (UT < 0) UT + 24 else UT
//        val localT = UT - 4
//
//        return localT
//    }
//}