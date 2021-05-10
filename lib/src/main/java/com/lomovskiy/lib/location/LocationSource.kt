package com.lomovskiy.lib.location

import android.location.Location

interface LocationSource {

    suspend fun getLastKnownLocation(): Location?

    suspend fun getCurrentLocation(): Location?

    fun getBestProvider(): String?

}