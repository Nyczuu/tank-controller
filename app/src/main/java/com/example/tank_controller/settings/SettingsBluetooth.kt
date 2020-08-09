package com.example.tank_controller.settings

import android.bluetooth.BluetoothAdapter
import kotlin.random.Random

private val service: SettingsBluetooth = SettingsBluetoothFake()

fun getBluetoothService() = service

interface SettingsBluetooth{
    suspend fun fetchSettings() : String
}

class SettingsBluetoothFake() : SettingsBluetooth {
    override suspend fun fetchSettings() =
        "{" +
                """ "temperature": ${Random.nextInt(20,40)},""" +
                """ "acceleration": ${Random.nextInt(0,15)},""" +
                """ "speed": ${Random.nextInt(0,10)},"""+
                """ "batterLevel": ${Random.nextInt(1,100)},"""+
                """ "pwmWidth": 10,"""+
                """ "powerVoltage": ${Random.nextDouble(9.0,10.0)},"""+
                """ "motorVoltage": ${Random.nextDouble(9.0,10.0)},"""+
                """ "coreVoltage": ${Random.nextDouble(9.0,10.0)},"""+
                """ "chargingCurrent": ${Random.nextInt(1,12)},"""+
                """ "charging": ${Random.nextBoolean()}"""+
            "}"
}