package com.hdapp.myapplication

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hdapp.myapplication.core.AppLocalization
import com.hdapp.myapplication.core.ProvideAppStrings
import com.hdapp.myapplication.feature.dashboard.DashboardScreen
import com.hdapp.myapplication.feature.dashboard.DashboardViewModel
import com.hdapp.myapplication.feature.login.LoginIntent
import com.hdapp.myapplication.feature.login.LoginScreen
import com.hdapp.myapplication.feature.login.LoginViewModel

@Composable
fun App(
    loginViewModel: LoginViewModel,
    dashboardViewModel: DashboardViewModel
) {
    val loginState by loginViewModel.state.collectAsState()
    val dashboardState by dashboardViewModel.state.collectAsState()

    println("App: Recomposing. User=${loginState.user?.username}, isArabic=${dashboardState.isArabic}")

    AppLocalization(isArabic = dashboardState.isArabic) {
        ProvideAppStrings(isArabic = dashboardState.isArabic) {
            MaterialTheme {
                if (loginState.user == null) {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = { 
                            println("App: onLoginSuccess callback called")
                        }
                    )
                } else {
                    DashboardScreen(
                        viewModel = dashboardViewModel,
                        onLogout = { 
                            println("App: onLogout callback called")
                            loginViewModel.onIntent(LoginIntent.Logout)
                        }
                    )
                }
            }
        }
    }
}
