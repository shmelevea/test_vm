package com.example.testvm.presentation.weatherday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testvm.R
import com.example.testvm.utils.DEFAULT_COORDINATES
import com.example.testvm.presentation.dialog.ErrorScreen
import com.example.testvm.presentation.dialog.LoadingScreen
import com.example.testvm.utils.RequestLocationPermission
import com.example.testvm.utils.getCurrentLocation
import com.example.testvm.viewmodels.WeatherViewModel

@Composable
fun DayScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    onShowForecastClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var showRationaleDialog by rememberSaveable { mutableStateOf(false) }
    var hasShownRationaleDialog by rememberSaveable { mutableStateOf(false) }

    RequestLocationPermission(
        onGranted = {
            getCurrentLocation(context) { location ->
                if (location != null) {
                    viewModel.updateLocation(location.latitude, location.longitude)
                } else {
                    viewModel.loadWeather(DEFAULT_COORDINATES)
                }
            }
        },
        onDenied = {
            viewModel.loadWeather(DEFAULT_COORDINATES)
        },
        onShowRationale = {
            if (!hasShownRationaleDialog) {
                showRationaleDialog = true
                hasShownRationaleDialog = true
            }
        }
    )

    if (showRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = { Text(stringResource(R.string.location_permission_required_title)) },
            text = { Text(stringResource(R.string.location_permission_required_text)) },
            confirmButton = {
                Button(onClick = {
                    showRationaleDialog = false
                    viewModel.loadWeather(DEFAULT_COORDINATES)
                }) {
                    Text(stringResource(R.string.continue_without_location))
                }
            }
        )
    }

    when {
        state.isLoading -> {
            LoadingScreen()
        }

        state.error != null -> {
            ErrorScreen(message = state.error!!)
        }

        state.currentWeather != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(
                        R.string.weather_in_place,
                        state.currentWeather!!.location.name
                    ),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.temp_c, state.currentWeather!!.current.tempC),
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    onShowForecastClick(viewModel.currentCoordinates)
                }) {
                    Text(stringResource(R.string.button_for_the_week))
                }
            }
        }

        else -> {
            ErrorScreen(stringResource(R.string.no_data))
        }
    }
}