package com.lomovskiy.lib.location

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission

interface LocationSource {

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ])
    @Throws(SecurityException::class)
    suspend fun getLastKnownLocation(): Location?

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun getCurrentLocation(): Location?

}