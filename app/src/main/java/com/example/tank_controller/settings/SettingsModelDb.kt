package com.example.tank_controller.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class SettingsModelDb constructor(
        var temperature:Int = 0,
        var acceleration: Int = 0,
        var speed: Int = 0,
        var batterLevel:Int = 0,
        var pwmWidth:Int = 0,
        var powerVoltage:Double= 0.0,
        var motorVoltage:Double= 0.0,
        var coreVoltage:Double = 0.0,
        var chargingCurrent:Int = 0,
        var charging:Boolean = false,
        @PrimaryKey val id: Int = 0)
