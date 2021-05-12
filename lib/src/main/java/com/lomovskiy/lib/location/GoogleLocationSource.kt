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
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GoogleLocationSource(
    context: Context,
    dispatcher: CoroutineDispatcher
) : AbsLocationSource(context, dispatcher) {

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    @Throws(SecurityException::class)
    override suspend fun getLastKnownLocation(): Location? {
        return withContext(dispatcher) {
            if (isAccessToFineLocationGranted || isAccessToCoarseLocationGranted) {
                return@withContext locationClient.lastLocation.await()
            }
            throw SecurityException()
        }
    }

    @Throws(SecurityException::class)
    override suspend fun getCurrentLocation(): Location? {
        if (isAccessToFineLocationGranted) {
            return getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY)
        }
        if (isAccessToCoarseLocationGranted) {
            return getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        }
        throw SecurityException()
    }

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    private suspend fun getCurrentLocation(priority: Int): Location? {
        val cancellationTokenSource = CancellationTokenSource()
        return locationClient.getCurrentLocation(
            priority,
            cancellationTokenSource.token
        ).await(cancellationTokenSource)
    }

}