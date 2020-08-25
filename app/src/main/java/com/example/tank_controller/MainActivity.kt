package com.example.tank_controller

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tank_controller.settings.SettingsActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var bluetoothReciver : BluetoothReciver? = null
    var bluetoothService : BluetoothService = BluetoothService.instance
    val REQUEST_ENABLE_BLUETOOTH = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConfigureReceiver()
        InitBluetooth()
        InitButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReciver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                if(bluetoothService.bluetoothAdapter!!.isEnabled) {
                    toast("Bluetooth has been enabled")
                } else {
                    toast("Bluetooth has been disabled")
                }
            } else if(resultCode == Activity.RESULT_CANCELED) {
                toast("Bluetooth enabling has been canceled")
            }
        }
    }

    private fun ConfigureReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        bluetoothReciver = BluetoothReciver()
        registerReceiver(bluetoothReciver, filter)
    }



    fun InitBluetooth() {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(bluetoothAdapter==null) {
            toast("This device doesn't support Bluetooth")
            return
        }

        if(!bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        if(bluetoothAdapter!!.isDiscovering()){
            bluetoothAdapter!!.cancelDiscovery()
        }

        if(bluetoothAdapter!!.bondedDevices.any()) {

        } else {
            bluetoothAdapter!!.startDiscovery()
        }
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

class BluetoothReciver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == BluetoothDevice.ACTION_FOUND) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        }
    }
}