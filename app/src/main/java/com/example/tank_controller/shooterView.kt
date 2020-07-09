package com.example.tank_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import com.example.tank_controller.dtos.settingsDto
import com.example.tank_controller.services.*
import kotlinx.coroutines.*

class shooterView : AppCompatActivity() {
    private var temperature: EditText? = null
    private var acceleration: EditText? = null
    private var speed: EditText? = null
    private var batteryLevel: EditText? = null
    private var pwmWidth: EditText? = null
    private var powerVoltage: EditText? = null
    private var motorVoltage: EditText? = null
    private var coreSupplyVoltage: EditText? = null
    private var charging: Switch? = null
    private var chargingCurrent: EditText? = null

    private var bluetoothService: bluetoothService = serviceLocator.bluetoothService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shooter_view)
        initializeFields()
        downloadData()
    }

    fun initializeFields() {
        temperature = findViewById<EditText>(R.id.temperatureDisplay)
        acceleration = findViewById<EditText>(R.id.accelerationDisplay)
        speed = findViewById<EditText>(R.id.speeedDisplay)
        batteryLevel = findViewById<EditText>(R.id.batteryLevelDisplay)
        pwmWidth = findViewById<EditText>(R.id.pwmWidthDisplay)
        powerVoltage = findViewById<EditText>(R.id.powerVoltageDisplay)
        motorVoltage = findViewById<EditText>(R.id.motorVoltageDisplay)
        coreSupplyVoltage = findViewById<EditText>(R.id.coreSupplyVoltageDisplay)
        charging = findViewById<Switch>(R.id.chargingDisplay)
        chargingCurrent = findViewById<EditText>(R.id.chargingCurrentDisplay)
    }

    fun downloadData() = GlobalScope.launch(Dispatchers.Main) {
        val data = bluetoothService.getSettings()
        delay(2000)
        refreshDashboard(data)
    }

    fun refreshDashboard(data: settingsDto) {
        temperature?.setText(data.temperature)
        acceleration?.setText(data.acceleration)
        speed?.setText(data.speed)
        batteryLevel?.setText(data.batterLevel)
        pwmWidth?.setText(data.pwmWidth)
        powerVoltage?.setText(data.powerVoltage.toString())
        motorVoltage?.setText(data.motorVoltage.toString())
        coreSupplyVoltage?.setText(data.coreVoltage.toString())
        chargingCurrent?.setText(data.chargingCurrent)
        charging?.setChecked(data.charging)

        downloadData()
    }
}

