package com.example.tank_controller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.random.Random

class BluetoothService  {
    companion object {
        val instance = BluetoothService()
    }

    val myUIID : UUID = UUID.randomUUID()
    var isConnected: Boolean = false
    var bluetoothDevice: BluetoothDevice? = null
    var bluetoothAdapter: BluetoothAdapter? = null
    var bluetoothSocket: BluetoothSocket? = null

    suspend fun fetchSettings(): String {
        return ""
    }

    fun init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter == null)
            return

        if(!bluetoothAdapter!!.bondedDevices.any())
            return

        bluetoothDevice = bluetoothAdapter!!.bondedDevices.first()
        connect()
    }

    fun readData(){
        val buffer = ByteArray(1024)
        var bytes: Int

        if(bluetoothSocket != null) {
            try {
                bytes = bluetoothSocket!!.inputStream.read(buffer)
                val readMessage = String(buffer, 0, bytes)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun sendCommand(command:String) {
        if(bluetoothSocket != null) {
            try{
                bluetoothSocket!!.outputStream.write(command.toByteArray())
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun connect() {
        try {
            if(bluetoothSocket == null || !isConnected) {
                if(bluetoothAdapter == null)
                    throw Exception("Bluetooth adapter must be configured before connection");
                if(bluetoothDevice == null)
                    throw Exception("Bluetooth device must be configured before connection");

                bluetoothSocket = bluetoothDevice!!.createInsecureRfcommSocketToServiceRecord(myUIID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                bluetoothSocket!!.connect()
                isConnected = true
            }
        } catch (e: IOException){
            e.printStackTrace()
        }
    }

    fun disconnect() {
        if(bluetoothSocket != null) {
            try {
                bluetoothSocket!!.close()
                bluetoothSocket = null
                isConnected = false
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
}