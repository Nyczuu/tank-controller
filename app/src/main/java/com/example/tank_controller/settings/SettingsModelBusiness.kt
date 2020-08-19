package com.example.tank_controller.settings

data class SettingsModelBusiness
    (val temperature:Int? = 0,
     val acceleration: Int? = 0,
     val speed: Int? = 0,
     val batterLevel:Int? = 0,
     val pwmWidth:Int? = 0,
     val powerVoltage:Double?= 0.0,
     val motorVoltage:Double?= 0.0,
     val coreVoltage:Double? = 0.0,
     val chargingCurrent:Int? = 0,
     val charging:Boolean? = false)
