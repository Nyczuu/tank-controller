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
    var speed:Int = 50
    var speedParsed:String = ""
    var direction:Int = 50
    var directionParsed:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_view)

        speedTextBox = findViewById(R.id. speedTextBox)

        moveForwardBackward = findViewById<SeekBar>(R.id.moveForwardBackward)
        moveForwardBackward.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                speed = i
                speedParsed = assignZero("$speed")
                directionParsed = assignZero("$direction")
                bluetoothService.sendCommand("M$speedParsed,$directionParsed")
                refreshCurrentSpeed()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        moveLeftRight = findViewById<SeekBar>(R.id.moveLeftRight)
        moveLeftRight.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                direction = i
                speedParsed = assignZero("$speed")
                directionParsed = assignZero("$direction")
                bluetoothService.sendCommand("M$speedParsed,$directionParsed")
                refreshCurrentSpeed()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

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
    fun assignZero(value: String):String
    {
        var result:String = ""
        if (value.length < 3)
        {
            result = value.padStart(3,'0')
        }
        else
        {
            result = value
        }
        return result
    }
}
