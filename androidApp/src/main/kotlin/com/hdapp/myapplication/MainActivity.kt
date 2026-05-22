package com.hdapp.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hdapp.myapplication.core.AppBuildContext
import com.hdapp.myapplication.core.AppEnvironment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize AppBuildContext
        // Note: You can set this based on build flavors or other logic
        AppBuildContext.environment = AppEnvironment.PROD

        setContent {
            App()
        }
    }
}
