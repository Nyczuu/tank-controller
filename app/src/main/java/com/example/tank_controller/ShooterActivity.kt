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
import android.widget.SeekBar
import org.jetbrains.anko.toast
import java.util.*

class ShooterActivity : AppCompatActivity() {

    val bluetoothService = BluetoothService()
    lateinit var firebutton:ImageButton
    lateinit var rotateTower:SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shooter_view)
        firebutton = findViewById<ImageButton>(R.id.fireButton)
        firebutton.setOnClickListener{ fire() }

        rotateTower = findViewById<SeekBar>(R.id.rotateTower)
        rotateTower.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                bluetoothService.sendCommand("S$i")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                toast("Rotation has been started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                toast("Rotation has been stopped")
            }
        })

    }

    private fun fire(){
        bluetoothService.sendCommand("fire")
        toast("fired!!")
    }
}

