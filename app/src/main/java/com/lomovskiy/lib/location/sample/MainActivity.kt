package com.lomovskiy.lib.location.sample

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lomovskiy.lib.location.AndroidLocationSource
import com.lomovskiy.lib.location.LocationSource
import com.lomovskiy.lib.ui.showToast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var locationSource: LocationSource

    private val launcherPermissions: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) {

    }

    private lateinit var buttonLastKnownLocation: Button
    private lateinit var buttonCurrentLocation: Button

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationSource = AndroidLocationSource(this)

        buttonLastKnownLocation = findViewById(R.id.button_last_known_location)
        buttonCurrentLocation = findViewById(R.id.button_current_location)

        launcherPermissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        buttonLastKnownLocation.setOnClickListener {
            MainScope().launch {
                val lastKnownLocation = locationSource.getLastKnownLocation()
                showToast(lastKnownLocation.toString())
            }
        }
        buttonCurrentLocation.setOnClickListener {
            MainScope().launch {
                val currentLocation = locationSource.getCurrentLocation()
                showToast(currentLocation.toString())
            }
        }
    }

}
