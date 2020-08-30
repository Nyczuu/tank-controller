package com.example.tank_controller

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.system.Os.socket
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tank_controller.settings.SettingsActivity
import org.jetbrains.anko.toast

import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    val bluetoothService = BluetoothService()

    lateinit var connectButton : Button
    lateinit var driverButton : Button
    lateinit var shooterButton : Button
    lateinit var settingsButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitButtons()
    }

    override fun onStart() {
        super.onStart()
        UpdateButtons()
    }

    fun InitButtons() {
        connectButton = findViewById<Button>(R.id.connectViewButton)
        connectButton.setOnClickListener{
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
        }

        driverButton = findViewById<Button>(R.id.driverViewButton)
        driverButton.setOnClickListener{
            val intent = Intent(this, DriverActivity::class.java)
            startActivity(intent)
        }

        shooterButton = findViewById<Button>(R.id.shooterViewButton)
        shooterButton.setOnClickListener{
            val intent = Intent(this, ShooterActivity::class.java)
            startActivity(intent)
        }

        settingsButton = findViewById<Button>(R.id.settingsViewButton)
        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    fun UpdateButtons() {
        if(bluetoothService.isConnectedWithDevice()) {
            settingsButton.isEnabled = true
            shooterButton.isEnabled = true
            driverButton.isEnabled = true
        }
        else {
            settingsButton.isEnabled = false
            shooterButton.isEnabled = false
            driverButton.isEnabled = false
        }
    }
}