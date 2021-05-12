package com.lomovskiy.lib.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import kotlinx.coroutines.CoroutineDispatcher

abstract class AbsLocationSource(
    protected val context: Context,
    protected val dispatcher: CoroutineDispatcher
) : LocationSource {

    protected val isAccessToFineLocationGranted: Boolean
        get() = context.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED

    protected val isAccessToCoarseLocationGranted: Boolean
        get() = context.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED

}
