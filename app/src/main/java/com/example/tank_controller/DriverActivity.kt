package com.example.tank_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.ToggleButton
import org.jetbrains.anko.toast

class DriverActivity : AppCompatActivity() {

    val bluetoothService = BluetoothService()
    lateinit var moveForwardBackward:SeekBar
    lateinit var moveLeftRight:SeekBar
    lateinit var engine:ToggleButton
    lateinit var lights:ToggleButton
    lateinit var speedTextBox:EditText
    var speed:Int = 128
    var direction:Int = 128


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_view)

        speedTextBox = findViewById(R.id. speedTextBox)

        moveForwardBackward = findViewById<SeekBar>(R.id.moveForwardBackward)
        moveForwardBackward.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                speed = i
                bluetoothService.sendCommand("M$speed,$direction")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                toast("Speed has been started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                toast("Speed has been stopped")
            }
        })

        moveLeftRight = findViewById<SeekBar>(R.id.moveLeftRight)
        moveLeftRight.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                direction = i
                bluetoothService.sendCommand("M$speed,$direction")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                toast("Direction has been started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                toast("Direction has been stopped")
            }
        })

        engine = findViewById(R.id.engine)
        engine.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                bluetoothService.sendCommand("E1")
                toast("Engine turned on")
            } else {
                bluetoothService.sendCommand("E0")
                toast("Engine is stopped")
            }
        }

        lights = findViewById(R.id.lights)
        lights.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                bluetoothService.sendCommand("L1")
                toast("Lights turned on")
            } else {
                bluetoothService.sendCommand("L0")
                toast("Lights are stopped")
            }
        }
    }
    fun refreshCurrentSpeed(){
        bluetoothService.sendCommand("speed")
        speedTextBox.setText(bluetoothService.readData())
    }
}
