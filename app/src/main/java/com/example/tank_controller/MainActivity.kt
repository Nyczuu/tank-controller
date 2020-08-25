package com.example.tank_controller

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tank_controller.settings.SettingsActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var bluetoothService : BluetoothService = BluetoothService.instance
    val REQUEST_ENABLE_BLUETOOTH = 1

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

    fun InitBluetooth() {
        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter == null)
            return

        if(bluetoothAdapter!!.isDiscovering())
            bluetoothAdapter!!.cancelDiscovery()

        bluetoothAdapter!!.startDiscovery()
        this.registerReceiver(SingBroadcastReceiver() , IntentFilter(BluetoothDevice.ACTION_FOUND))

        //if(!bluetoothAdapter!!.bondedDevices.any())
         //   return

        //bluetoothDevice = bluetoothAdapter!!.bondedDevices.first()

/*
        if(bluetoothService.bluetoothAdapter==null) {
            toast("This device doesn't support Bluetooth")
            return
        }

        if(!bluetoothService.bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        if(bluetoothService.bluetoothDevice == null){
            toast("No connected device found")
            return
        }
 */
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

private class SingBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val action =
            intent.action
        if (BluetoothDevice.ACTION_FOUND == action) {

            val device =
                intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        }
    }
}