package com.lomovskiy.lib.location.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lomovskiy.lib.location.AndroidLocationSource
import com.lomovskiy.lib.location.LocationSource
import com.lomovskiy.lib.ui.showToast
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var locationSource: LocationSource

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationSource = AndroidLocationSource(this, Executors.newSingleThreadExecutor())
        button = findViewById(R.id.button)
        button.setOnClickListener {
            val bestProvider = locationSource.getBestProvider()
            showToast(bestProvider ?: "null")
        }
    }

}
