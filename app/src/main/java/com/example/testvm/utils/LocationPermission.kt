package com.example.testvm.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import android.Manifest
import com.google.android.gms.location.LocationServices
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onGranted: () -> Unit,
    onDenied: () -> Unit,
    onShowRationale: () -> Unit
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var permissionRequested by remember { mutableStateOf(false) }

    LaunchedEffect(permissionState.status) {
        when (permissionState.status) {
            is PermissionStatus.Granted -> {
                onGranted()
            }
            is PermissionStatus.Denied -> {
                if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                    onShowRationale()
                } else if (!permissionRequested) {
                    permissionState.launchPermissionRequest()
                    permissionRequested = true
                } else {
                    onDenied()
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onResult: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            onResult(location)
        }
        .addOnFailureListener {
            onResult(null)
        }
}