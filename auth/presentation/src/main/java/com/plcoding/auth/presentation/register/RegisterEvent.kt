package com.plcoding.auth.presentation.register

import com.plcoding.core.presentation.ui.UiText

sealed interface RegisterEvent {

    data class Error(val error: UiText): RegisterEvent

    data object RegistrationSuccess: RegisterEvent
}