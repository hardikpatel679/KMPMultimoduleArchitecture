package com.hdapp.myapplication.feature.login

import com.hdapp.myapplication.core.NetworkError
import com.hdapp.myapplication.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: NetworkError? = null
)

sealed interface LoginIntent {
    data class Login(val username: String, val password: String) : LoginIntent
    data object ClearError : LoginIntent
    data object Logout : LoginIntent
}

sealed interface LoginEffect {
    data object NavigateToHome : LoginEffect
    data class ShowSnackbar(val error: NetworkError) : LoginEffect
}
