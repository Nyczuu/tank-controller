package com.example.tank_controller.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tank_controller.utilities.singleArgViewModelFactory
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel(){
    companion object {
        val FACTORY = singleArgViewModelFactory(::SettingsViewModel)
    }

    val settings = repository.currentSettings

    fun onRefreshButtonClicked(){
        refreshSettings()
    }

    private fun refreshSettings() {
        viewModelScope.launch {
            repository.refreshSettings()
        }
    }
}