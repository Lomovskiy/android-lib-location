package com.lomovskiy.lib.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Process
import androidx.annotation.RequiresPermission
import androidx.core.location.LocationManagerCompat
import androidx.core.os.CancellationSignal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume

class AndroidLocationSource(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val executor: Executor = Executors.newSingleThreadExecutor()
) : LocationSource {

    private val criteria: Criteria = Criteria().apply {
        accuracy = Criteria.ACCURACY_FINE
    }

    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    @Throws(SecurityException::class)
    override suspend fun getLastKnownLocation(): Location? {
        return withContext(dispatcher) {
            if (context.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
                return@withContext locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            if (context.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
                return@withContext locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            throw SecurityException()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun getCurrentLocation(): Location? {
        return suspendCancellableCoroutine { cont ->
            val cancellationSignal = CancellationSignal()
            LocationManagerCompat.getCurrentLocation(
                locationManager,
                getBestLocationProvider(),
                cancellationSignal,
                executor,
                cont::resume
            )
            cont.invokeOnCancellation {
                cancellationSignal.cancel()
            }
        }
    }

    private fun getBestLocationProvider(): String {
        val bestProvider: String? = locationManager.getBestProvider(criteria, true)
        return bestProvider ?: LocationManager.PASSIVE_PROVIDER
    }

}