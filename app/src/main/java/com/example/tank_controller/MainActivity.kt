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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitButtons()
    }

    fun InitButtons() {
        val connectButton = findViewById<Button>(R.id.connectViewButton)
        connectButton.setOnClickListener{
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
        }

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


//        if(bluetoothAdapter!!.bondedDevices.any()) {
//            val macAddress:String = "98:D3:31:FB:55:E6"
////            val macAddress:String = "D0:7F:A0:18:E3:D8"
//
//            val password = "1234"
//            val device =
//                bluetoothAdapter!!.bondedDevices.first{ it.address == macAddress }
//            val myUIID : UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                //device!!.setPin(password.toByteArray())
//                //device!!.setPairingConfirmation(true)
//
//            }
//            var  bluetoothSocket = device!!.createInsecureRfcommSocketToServiceRecord(myUIID)
//            //BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
//            bluetoothAdapter!!.cancelDiscovery()
//            try{
//                bluetoothSocket!!.connect()
//            }
//            catch(e: IOException){
//                var deviceJava = bluetoothSocket.remoteDevice.javaClass
//                var paramTypes = arrayOf<Class<*>>(Integer.TYPE)
//                bluetoothSocket = deviceJava.getMethod("createRfcommSocket", *paramTypes).invoke(bluetoothSocket.remoteDevice, Integer.valueOf(2)) as BluetoothSocket
//                bluetoothSocket!!.connect()
//            }
//            val command:String = "A00000"
//            try{
//                bluetoothSocket!!.outputStream.write(command.toByteArray())
//            } catch (e: IOException){
//                e.printStackTrace()
//            }
//        }

