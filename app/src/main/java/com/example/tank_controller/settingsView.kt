package com.example.tank_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import com.example.tank_controller.dtos.settingsDto
import com.example.tank_controller.services.bluetoothService
import kotlinx.android.synthetic.main.activity_settings_view.*
import kotlinx.coroutines.*

class settingsView : AppCompatActivity(), CoroutineScope by MainScope() {
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

    private var bluetoothService: bluetoothService = bluetoothService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_view)

        refresh.setOnClickListener{
            launch {
                downloadData()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    private suspend fun downloadData() {
        val result = bluetoothService.getSettings()
            withContext(Dispatchers.Default) {
               refreshDashboard(result)
            }
    }

    private fun refreshDashboard(data: settingsDto) {
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
    }
}
