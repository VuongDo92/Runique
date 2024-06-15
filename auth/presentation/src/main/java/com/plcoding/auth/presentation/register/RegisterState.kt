package com.plcoding.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.plcoding.auth.domain.PasswordValidationState

@OptIn(ExperimentalFoundationApi::class)
data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val emailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false // passwordValidationState.isValidPassword && !isRegistering
)
