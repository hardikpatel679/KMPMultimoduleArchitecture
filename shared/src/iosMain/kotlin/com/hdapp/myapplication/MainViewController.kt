package com.hdapp.myapplication

import androidx.compose.ui.window.ComposeUIViewController
import com.hdapp.myapplication.core.createHttpClient
import com.hdapp.myapplication.data.repository.LoginRepositoryImpl
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import com.hdapp.myapplication.feature.login.LoginViewModel
import com.hdapp.myapplication.feature.dashboard.DashboardViewModel

fun MainViewController() = ComposeUIViewController {
    val httpClient = createHttpClient()
    val repository = LoginRepositoryImpl(httpClient)
    val loginUseCase = LoginUseCase(repository)
    val loginViewModel = LoginViewModel(loginUseCase)
    val dashboardViewModel = DashboardViewModel()
    
    App(
        loginViewModel = loginViewModel,
        dashboardViewModel = dashboardViewModel
    )
}
