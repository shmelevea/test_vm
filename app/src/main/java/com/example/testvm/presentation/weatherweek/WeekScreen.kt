package com.example.testvm.presentation.weatherweek

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testvm.R
import com.example.testvm.utils.DEFAULT_COORDINATES
import com.example.testvm.presentation.dialog.ErrorScreen
import com.example.testvm.presentation.dialog.LoadingScreen
import com.example.testvm.viewmodels.WeatherViewModel

@Composable
fun WeekScreen(
    coordinates: String,
    onBackClick: () -> Unit
) {
    val viewModel: WeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(coordinates) {
        val safeCoordinates = coordinates.ifBlank { DEFAULT_COORDINATES }
        viewModel.loadWeather(safeCoordinates)
    }

    val context = LocalContext.current
    LaunchedEffect(state.forecastWeather) {
        state.forecastWeather?.let { forecast ->
            if (forecast.forecast.forecastday.size < 7) {
                Toast.makeText(
                    context,
                    context.getString(R.string.limitation_free_api_3),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    when {
        state.isLoading -> {
            LoadingScreen()
        }

        state.error != null -> {
            ErrorScreen(state.error!!)
        }

        state.forecastWeather != null -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.week_weather_title),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                items(state.forecastWeather!!.forecast.forecastday) { day ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = day.date, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(
                                    R.string.average_temperature,
                                    day.day.avgtempC
                                )
                            )
                            Text(
                                text = stringResource(
                                    R.string.max_temperature,
                                    day.day.maxtemp_c
                                )
                            )
                            Text(
                                text = stringResource(
                                    R.string.min_temperature,
                                    day.day.mintempC
                                )
                            )

                            Text(text = day.day.condition.text)
                        }
                    }
                }
            }
        }

        else -> {
            ErrorScreen(stringResource(R.string.no_data))
        }
    }

    BackHandler(onBack = onBackClick)
}
