package com.hdapp.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.hdapp.myapplication.feature.login.AndroidLoginViewModel
import com.hdapp.myapplication.feature.login.LoginViewModel
import com.hdapp.myapplication.feature.dashboard.AndroidDashboardViewModel
import com.hdapp.myapplication.feature.dashboard.DashboardViewModel
import com.hdapp.myapplication.core.AppEnvironment
import com.hdapp.myapplication.core.BuildContext
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize BuildContext based on Flavor
        BuildContext.environment = when (BuildConfig.FLAVOR) {
            "dev" -> AppEnvironment.DEV
            "mock" -> AppEnvironment.MOCK
            else -> AppEnvironment.PROD
        }

        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel<AndroidLoginViewModel>()
            val dashboardViewModel: DashboardViewModel = hiltViewModel<AndroidDashboardViewModel>()
            App(
                loginViewModel = loginViewModel,
                dashboardViewModel = dashboardViewModel
            )
        }
    }
}
