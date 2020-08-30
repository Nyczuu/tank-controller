package com.example.tank_controller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.io.IOException
import java.lang.Exception
import java.net.Inet4Address
import java.util.*


class BluetoothService  {
    companion object {
        var bluetoothAdapter : BluetoothAdapter? = null
        var bluetoothSocket : BluetoothSocket? = null
    }

    suspend fun fetchSettings(): String {
        return ""
    }

    fun isConnectedWithDevice(): Boolean {
        if(bluetoothSocket == null)
            return false

        return bluetoothSocket!!.isConnected
    }

    fun setBluetoothAdapter(adapter: BluetoothAdapter) {
        bluetoothAdapter = adapter
    }

    fun getPairedDevices(): Set<BluetoothDevice> {
        if(bluetoothAdapter == null)
            Exception("You must set Bluetooth adapter first!")

        return bluetoothAdapter!!.bondedDevices
    }

    fun selectDevice(address: String) {
        if(bluetoothAdapter == null)
            Exception("You must set Bluetooth adapter first!")

        val device = bluetoothAdapter!!.bondedDevices.first { it.address == address }
        val myUIID : UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        bluetoothSocket = device!!.createInsecureRfcommSocketToServiceRecord(myUIID)

        try {
            bluetoothSocket!!.connect()
        }
        catch(e: IOException) {
            var deviceJava = bluetoothSocket!!.remoteDevice.javaClass
            var paramTypes = arrayOf<Class<*>>(Integer.TYPE)
            bluetoothSocket = deviceJava.getMethod("createRfcommSocket", *paramTypes).invoke(bluetoothSocket!!.remoteDevice, Integer.valueOf(2)) as BluetoothSocket
            bluetoothSocket!!.connect()
        }
    }

    fun readData(){
        val buffer = ByteArray(1024)
        var bytes: Int

        if(bluetoothSocket == null)
            Exception("You must connect to Bluetooth device first!")

            try {
                bytes = bluetoothSocket!!.inputStream.read(buffer)
                val readMessage = String(buffer, 0, bytes)
            } catch (e: IOException){
                e.printStackTrace()
            }
    }

    fun sendCommand(command:String) {
        if(bluetoothSocket == null)
            Exception("You must connect to Bluetooth device first!")

            try{
                bluetoothSocket!!.outputStream.write(command.toByteArray())
            } catch (e: IOException){
                e.printStackTrace()
            }

    }
}


