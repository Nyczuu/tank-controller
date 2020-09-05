package com.example.tank_controller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.tank_controller.settings.SettingsModelBusiness
import java.io.IOException
import java.lang.Exception
import java.net.Inet4Address
import java.util.*


class BluetoothService  {
    companion object {
        var bluetoothAdapter : BluetoothAdapter? = null
        var bluetoothSocket : BluetoothSocket? = null
    }

    suspend fun fetchSettings(): SettingsModelBusiness {
        sendCommand("settings")
        Thread.sleep(1_000)
        var data = readData().split(',')
        return SettingsModelBusiness(
            data[0].toIntOrNull(),
            data[1].toIntOrNull(),
            data[2].toIntOrNull(),
            data[3].toIntOrNull(),
            data[4].toIntOrNull(),
            data[5].toDoubleOrNull(),
            data[6].toDoubleOrNull(),
            data[7].toDoubleOrNull(),
            data[8].toIntOrNull(),
            data[9].toBoolean()
            )
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

    fun readData():String{
        val buffer = ByteArray(1024)
        var bytes: Int
        var result:String = ""

        if(bluetoothSocket == null)
            Exception("You must connect to Bluetooth device first!")

            try {
                bytes = bluetoothSocket!!.inputStream.read(buffer)
                result = String(buffer, 0, bytes)
            } catch (e: IOException){
                e.printStackTrace()
            }
        return result
    }

    fun sendCommand(command:String) {
        var defaultCommandLength = 10

        if(bluetoothSocket == null)
            Exception("You must connect to Bluetooth device first!")

        if(command.length > defaultCommandLength)
            Exception("Command can have max 10 characters")

        var commandPadded = command.padEnd(defaultCommandLength,'_')

        try {
            bluetoothSocket!!.outputStream.write(commandPadded.toByteArray())
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}


