@file:OptIn(ExperimentalFoundationApi::class)

package com.plcoding.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.domain.AuthRepository
import com.plcoding.auth.domain.UserDataValidator
import com.plcoding.auth.presentation.R
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.Result
import com.plcoding.core.presentation.ui.UiText
import com.plcoding.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val evenChannel = Channel<LoginEvent>()
    val events = evenChannel.receiveAsFlow()

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(email.toString().trim()) && password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when(action) {
            is LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            is LoginAction.OnLoginClick -> {
                login()
            }
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString().trim()
            )
            state = state.copy(isLoggingIn = false)
            when(result) {
                is Result.Error -> {
                    if(result.error == DataError.Network.UNAUTHORIZED) {
                        evenChannel.send(LoginEvent.Error(UiText.StringResource(R.string.error_email_password_incorrect)))

                    } else {
                        evenChannel.send(LoginEvent.Error(result.error.asUiText()))

                    }
                }
                is Result.Success -> {
                    evenChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}