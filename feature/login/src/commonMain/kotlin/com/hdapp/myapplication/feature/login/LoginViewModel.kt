package com.hdapp.myapplication.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdapp.myapplication.core.NetworkError
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>(extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> performLogin(intent.username, intent.password)
            is LoginIntent.ClearError -> _state.update { it.copy(error = null) }
            is LoginIntent.Logout -> _state.update { it.copy(user = null, isLoading = false, error = null) }
        }
    }

    private fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val result = loginUseCase(username, password)
            
            result.onSuccess { user ->
                _state.update { it.copy(isLoading = false, user = user) }
                _effect.emit(LoginEffect.NavigateToHome)
            }.onFailure { error ->
                val networkError = error as? NetworkError ?: NetworkError.Unknown(error.message ?: "Unknown error")
                _state.update { it.copy(isLoading = false, error = networkError) }
                _effect.emit(LoginEffect.ShowSnackbar(networkError))
            }
        }
    }
}
