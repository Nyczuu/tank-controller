package com.example.tank_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tank_controller.dtos.settingsDto
import com.example.tank_controller.services.*
import kotlinx.coroutines.*

class shooterView : AppCompatActivity() {
    private var bluetoothService: bluetoothService = serviceLocator.bluetoothService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shooter_view)

        downloadData()
    }

    fun downloadData() = GlobalScope.launch(Dispatchers.Main) {
        val data = bluetoothService.getSettings()
        refreshDashboard(data)

    }

    fun refreshDashboard(data: settingsDto) {

    }
}

