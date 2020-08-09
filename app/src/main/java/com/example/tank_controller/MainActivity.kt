package com.example.tank_controller

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tank_controller.settings.SettingsActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var _bluetoothAdapter :BluetoothAdapter? = null
    lateinit var pairedDevice: BluetoothDevice
    val REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InitBluetooth()
        InitButtons()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                if(_bluetoothAdapter!!.isEnabled) {
                    toast("Bluetooth has been enabled")
                } else {
                    toast("Bluetooth has been disabled")
                }
            } else if(resultCode == Activity.RESULT_CANCELED) {
                toast("Bluetooth enabling has been canceled")
            }
        }
    }

    fun InitBluetooth() {
        _bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(_bluetoothAdapter==null) {
            toast("This device doesn't support Bluetooth")
            return
        }

        if(!_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        //TODO: Zrobić coś z tym urzadzeniem XD
    }

    fun InitButtons() {
        val driverButton = findViewById<Button>(R.id.driverViewButton)
        driverButton.setOnClickListener{
            val intent = Intent(this, DriverActivity::class.java)
            startActivity(intent)
        }

        val shooterButton = findViewById<Button>(R.id.shooterViewButton)
        shooterButton.setOnClickListener{
            val intent = Intent(this, ShooterActivity::class.java)
            startActivity(intent)
        }

        val settingsButton = findViewById<Button>(R.id.settingsViewButton)
        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
