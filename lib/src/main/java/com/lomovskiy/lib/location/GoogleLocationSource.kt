package com.lomovskiy.lib.location

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GoogleLocationSource(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val executor: Executor = Executors.newSingleThreadExecutor()
) : LocationSource {

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)
    private val settingsClient = LocationServices.getSettingsClient(context)

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    @Throws(SecurityException::class)
    override suspend fun getLastKnownLocation(): Location? {
        return locationClient.lastLocation.await(executor)
    }

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    override suspend fun getCurrentLocation(): Location? {
        val cancellationTokenSource = CancellationTokenSource()
        return locationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).await(executor, cancellationTokenSource)
    }

}