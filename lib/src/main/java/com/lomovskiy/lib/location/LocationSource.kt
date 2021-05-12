package com.lomovskiy.lib.location

import android.location.Location

interface LocationSource {

    @Throws(SecurityException::class)
    suspend fun getLastKnownLocation(): Location?

    @Throws(SecurityException::class)
    suspend fun getCurrentLocation(): Location?

}
