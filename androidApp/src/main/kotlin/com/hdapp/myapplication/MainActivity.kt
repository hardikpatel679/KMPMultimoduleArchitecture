package com.hdapp.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hdapp.myapplication.core.AppEnvironment
import com.hdapp.myapplication.core.AppBuildContext
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize AppBuildContext based on Flavor if available
        val flavor = getFlavorName()
        AppBuildContext.environment = when {
            flavor.contains("dev") -> AppEnvironment.DEV
            flavor.contains("mock") -> AppEnvironment.MOCK
            else -> AppEnvironment.PROD
        }

        setContent {
            App()
        }
    }

    private fun getFlavorName(): String {
        return try {
            val field = BuildConfig::class.java.getField("FLAVOR")
            field.get(null) as String
        } catch (_: Exception) {
            ""
        }
    }
}
