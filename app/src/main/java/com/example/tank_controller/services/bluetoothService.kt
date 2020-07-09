package com.example.tank_controller.services

import com.example.tank_controller.dtos.settingsDto
import kotlin.random.Random

class bluetoothService {
    //TODO: Implement real bluetooth connection
    fun getSettings() : settingsDto {
        return settingsDto(
            temperature = Random.nextInt(20,60),
            acceleration = Random.nextInt(20,60),
            speed = Random.nextInt(0,100),
            batterLevel = Random.nextInt(0,100),
            pwmWidth = Random.nextInt(0,3),
            powerVoltage = Random.nextDouble(10.0, 12.0),
            motorVoltage = Random.nextDouble(10.0, 12.0),
            coreVoltage = Random.nextDouble(10.0, 12.0),
            chargingCurrent = Random.nextInt(0,100),
            charging = false
        )
    }
}