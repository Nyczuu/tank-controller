package com.example.tank_controller.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class SettingsRepository(val bluetooth: SettingsBluetooth, val settingsDao: SettingsDao) {
    val currentSettings: LiveData<SettingsModelBusiness?> = settingsDao.settingsLiveData.map { item -> SettingsModelBusiness(
        item!!.temperature, item.acceleration, item.speed, item.batterLevel, item.pwmWidth, item.powerVoltage, item.motorVoltage, item.coreVoltage, item.chargingCurrent, item.charging ) }

    suspend fun refreshSettings() {
        try
        {
            val resultJson = withTimeout(5_000) {
                bluetooth.fetchSettings()
            }

            val resultParsed = Gson().fromJson(resultJson, SettingsModelBusiness::class.java)

            settingsDao.insertSettings(SettingsModelDb(temperature = resultParsed.temperature, acceleration =  resultParsed.acceleration, speed = resultParsed.speed,
                batterLevel = resultParsed.batterLevel, pwmWidth = resultParsed.pwmWidth, powerVoltage = resultParsed.powerVoltage, motorVoltage = resultParsed.motorVoltage,
                coreVoltage =  resultParsed.coreVoltage, chargingCurrent = resultParsed.chargingCurrent, charging = resultParsed.charging))

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

