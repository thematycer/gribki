package com.nprg056.gribki.settings

sealed interface SettingsEvent {
    data class ChangeFontSize(val fontSize: Float) : SettingsEvent

    object SaveSettings : SettingsEvent
}
