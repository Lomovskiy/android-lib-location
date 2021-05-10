package com.lomovskiy.lib.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Process
import androidx.core.location.LocationManagerCompat
import androidx.core.os.CancellationSignal
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.jvm.Throws

class AndroidLocationSource(
    private val context: Context,
    private val executor: Executor
) : LocationSource {

    private val criteria: Criteria = Criteria().apply {
        accuracy = Criteria.ACCURACY_FINE
    }

    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override suspend fun getLastKnownLocation(): Location? {
        if (context.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            val gpsLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (gpsLocation != null) {
                return gpsLocation
            }
        }
        if (context.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            val networkLocation: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (networkLocation != null) {
                return networkLocation
            }
        }
        return locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
    }

    @Throws(SecurityException::class)
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

    override fun getBestProvider(): String? {
        return locationManager.getBestProvider(criteria, false)
    }

    private fun getBestLocationProvider(): String {
        val bestProvider: String? = locationManager.getBestProvider(criteria, true)
        return bestProvider ?: LocationManager.PASSIVE_PROVIDER
    }

}