package com.example.tank_controller.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.example.tank_controller.*
import kotlin.random.Random

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings_view)

        var temperature: EditText = findViewById(R.id.temperatureDisplay)
        val acceleration: EditText = findViewById(R.id.accelerationDisplay)
        val speed: EditText = findViewById(R.id.speeedDisplay)
        val batteryLevel: EditText = findViewById(R.id.batteryLevelDisplay)
        val pwmWidth: EditText = findViewById(R.id.pwmWidthDisplay)
        val powerVoltage: EditText = findViewById(R.id.powerVoltageDisplay)
        val motorVoltage: EditText = findViewById(R.id.motorVoltageDisplay)
        val coreVoltage: EditText = findViewById(R.id.coreSupplyVoltageDisplay)
        val chargingCurrent: EditText = findViewById(R.id.chargingCurrentDisplay)
        val charging: Switch = findViewById(R.id.chargingDisplay)

        val database = getDatabase(this)
        val repository = SettingsRepository(
            getBluetoothService(),
            database.settingsDao
        )
        val viewModel = ViewModelProviders
            .of(this,
                SettingsViewModel.FACTORY(
                    repository
                )
            )
            .get(SettingsViewModel::class.java)

        viewModel.settings.observe(this) { value ->
            value?.let {
                temperature?.setText("${value.temperature}")
                acceleration?.setText("${value.acceleration}")
                speed?.setText("${value.speed}")
                batteryLevel?.setText("${value.batterLevel}")
                pwmWidth?.setText("${value.pwmWidth}")
                powerVoltage?.setText("${value.powerVoltage}")
                motorVoltage?.setText("${value.motorVoltage}")
                coreVoltage?.setText("${value.coreVoltage}")
                chargingCurrent?.setText("${value.chargingCurrent}")
                charging?.setChecked(value.charging)
            }
        }

        val shooterButton = findViewById<Button>(R.id.refresh_settings)
        shooterButton.setOnClickListener {
            viewModel.onRefreshButtonClicked()
        }
    }
}
