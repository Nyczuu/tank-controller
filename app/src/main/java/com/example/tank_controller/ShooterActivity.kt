package com.example.tank_controller

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import org.jetbrains.anko.toast
import java.util.*

class ShooterActivity : AppCompatActivity() {

    val bluetoothService = BluetoothService()
    lateinit var firebutton:ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shooter_view)
        firebutton = findViewById<ImageButton>(R.id.fireButton)
        firebutton.setOnClickListener{ fire() }

    }

    private fun fire(){
        bluetoothService.sendCommand("settings")
        val data = bluetoothService.readData()
        toast(data)
    }
}

