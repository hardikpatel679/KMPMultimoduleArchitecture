package com.hdapp.myapplication.feature.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.core.strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToLogin -> onLogout()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.dashboardTitle) },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (state.isArabic) "العربية" else "English",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = state.isArabic,
                            onCheckedChange = { viewModel.onIntent(DashboardIntent.ToggleLanguage) },
                            modifier = Modifier.testTag(TestTags.DASHBOARD_LANGUAGE_SWITCH)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = strings.dashboardWelcome,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.testTag(TestTags.DASHBOARD_WELCOME_TEXT)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.onIntent(DashboardIntent.Logout) },
                modifier = Modifier.testTag(TestTags.DASHBOARD_LOGOUT_BUTTON)
            ) {
                Text(strings.appLogout)
            }
        }
    }
}
