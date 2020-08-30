package com.example.tank_controller.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.tank_controller.BluetoothService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class SettingsRepository(val settingsDao: SettingsDao) {
    val currentSettings: LiveData<SettingsModelBusiness?> = settingsDao.settingsLiveData.map { item ->
        SettingsModelBusiness(item?.temperature, item?.acceleration, item?.speed, item?.batterLevel, item?.pwmWidth, item?.powerVoltage, item?.motorVoltage, item?.coreVoltage, item?.chargingCurrent, item?.charging )
    }

    suspend fun refreshSettings() {
        try
        {
            val resultJson = withTimeout(5_000) {
                BluetoothService().fetchSettings()
            }

            val resultParsed = Gson().fromJson(resultJson, SettingsModelBusiness::class.java)

            settingsDao.insertSettings(SettingsModelDb(
                temperature = resultParsed.temperature?:0,
                acceleration =  resultParsed.acceleration?:0,
                speed = resultParsed.speed?:0,
                batterLevel = resultParsed.batterLevel?:0,
                pwmWidth = resultParsed.pwmWidth?:0,
                powerVoltage = resultParsed.powerVoltage?:0.0,
                motorVoltage = resultParsed.motorVoltage?:0.0,
                coreVoltage =  resultParsed.coreVoltage?:0.0,
                chargingCurrent = resultParsed.chargingCurrent?:0,
                charging = resultParsed.charging?:false))

        } catch (error: Throwable){
            throw SettingsRefreshError("Unable to refresh settings", error)
        }
    }

    fun refreshSettingsInterop(settingsRefreshCallback: SettingsRefreshCallback) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                refreshSettings()
                settingsRefreshCallback.onCompleted()
            } catch (throwable: Throwable) {
                settingsRefreshCallback.onError(throwable)
            }
        }
    }
}

class SettingsRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

interface SettingsRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}

