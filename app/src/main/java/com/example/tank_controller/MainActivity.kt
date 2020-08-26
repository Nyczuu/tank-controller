package com.example.tank_controller

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tank_controller.settings.SettingsActivity
import org.jetbrains.anko.toast
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    //var bluetoothReciver : BluetoothReciver? = null
    var bluetoothService : BluetoothService = BluetoothService.instance
    val REQUEST_ENABLE_BLUETOOTH = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //CheckPermissions()
        //ConfigureReceiver()
        InitBluetooth()
        InitButtons()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(bluetoothReciver)
//    }

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

    private fun CheckPermissions() {
        val permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
        val permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)
        val permission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val permission4 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission1 != PackageManager.PERMISSION_GRANTED ||
            permission2 != PackageManager.PERMISSION_GRANTED ||
            permission3 != PackageManager.PERMISSION_GRANTED
            || permission4 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION),
                642)
        } else {
            Log.d("DISCOVERING-PERMISSIONS", "Permissions Granted")
        }
    }

//    private fun ConfigureReceiver() {
//
//        val filter = IntentFilter()
//        filter.addAction(BluetoothDevice.ACTION_FOUND)
//        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
//        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
//        //bluetoothReciver = BluetoothReciver()
//        registerReceiver(bluetoothReciver, filter)
//    }



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
/*
        if(bluetoothAdapter!!.isDiscovering()){
            bluetoothAdapter!!.cancelDiscovery()
        }

 */

        if(bluetoothAdapter!!.bondedDevices.any()) {
//            val macAddress:String = "98:D3:31:FB:55:E6"
            val macAddress:String = "D0:7F:A0:18:E3:D8"

            val password = "1234"
            val device =
                bluetoothAdapter!!.bondedDevices.first{ it.address == macAddress }
            val myUIID : UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //device!!.setPin(password.toByteArray())
                //device!!.setPairingConfirmation(true)

            }
            var  bluetoothSocket = device!!.createInsecureRfcommSocketToServiceRecord(myUIID)
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()

            bluetoothSocket!!.connect()
            val command:String = "A000"
            try{
                bluetoothSocket!!.outputStream.write(command.toByteArray())
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
        /*
        else {
            bluetoothAdapter!!.startDiscovery()
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

/*class BluetoothReciver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        print("BroadcastReceiver.OnReceive")
        if (intent.action == BluetoothDevice.ACTION_FOUND || intent.action == BluetoothDevice.ACTION_PAIRING_REQUEST) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                device.createBond()
            }
        }
    }
}

 */