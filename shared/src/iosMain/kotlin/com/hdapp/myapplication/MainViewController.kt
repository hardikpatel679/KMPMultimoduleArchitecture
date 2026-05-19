package com.hdapp.myapplication

import androidx.compose.ui.window.ComposeUIViewController
import com.hdapp.myapplication.di.KmpDI

fun MainViewController() = ComposeUIViewController {
    App(
        loginViewModel = KmpDI.createLoginViewModel(),
        dashboardViewModel = KmpDI.createDashboardViewModel()
    )
}
