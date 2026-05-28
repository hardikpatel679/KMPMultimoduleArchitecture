package com.hdapp.myapplication.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hdapp.myapplication.core.Dimens
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.core.getLocalizedMessage
import com.hdapp.myapplication.core.localizedMessage
import com.hdapp.myapplication.core.strings
import myapplication.core.generated.resources.Res
import myapplication.core.generated.resources.ic_logo
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val currentStrings = strings
    var username by remember { mutableStateOf("emilys") }
    var password by remember { mutableStateOf("emilyspass") }
    var passwordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome -> {
                    onLoginSuccess()
                }
                is LoginEffect.ShowSnackbar -> {
                    val errorMessage = effect.error.getLocalizedMessage(currentStrings)
                    snackbarHostState.showSnackbar(
                        message = errorMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        Box(
            modifier = Modifier
                .size(Dimens.logoSize)
                .testTag(TestTags.LOGIN_LOGO),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_logo),
                contentDescription = "App Logo",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(Dimens.spacingLarge))

        Text(
            text = strings.loginWelcome,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(Dimens.spacingSmall))
        
        Text(
            text = strings.loginSubtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(Dimens.spacingExtraLarge))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(strings.loginUsernameLabel) },
            placeholder = { Text(strings.loginUsernamePlaceholder) },
            modifier = Modifier.fillMaxWidth().testTag(TestTags.LOGIN_USERNAME_FIELD),
            singleLine = true,
            enabled = !state.isLoading
        )

        Spacer(modifier = Modifier.height(Dimens.spacingMedium))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(strings.loginPasswordLabel) },
            placeholder = { Text(strings.loginPasswordPlaceholder) },
            trailingIcon = {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(if (passwordVisible) strings.loginHidePassword else strings.loginShowPassword)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth().testTag(TestTags.LOGIN_PASSWORD_FIELD),
            singleLine = true,
            enabled = !state.isLoading
        )

        if (state.error != null) {
            Text(
                text = state.error?.localizedMessage(strings) ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = Dimens.paddingSmall).testTag(TestTags.LOGIN_ERROR_MESSAGE)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.spacingLarge))

        Button(
            onClick = { 
                viewModel.onIntent(LoginIntent.Login(username, password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.buttonHeight)
                .testTag(TestTags.LOGIN_BUTTON),
            shape = MaterialTheme.shapes.medium,
            enabled = !state.isLoading && username.isNotEmpty() && password.isNotEmpty()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.spacingLarge),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(strings.loginButton, fontSize = Dimens.textLarge)
            }
        }

        Spacer(modifier = Modifier.height(Dimens.spacingMedium))

        TextButton(onClick = { /* Handle Forgot Password */ }) {
            Text(strings.loginForgotPassword)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(strings.loginNoAccount)
            TextButton(onClick = { /* Handle Sign Up */ }) {
                Text(strings.loginSignUp)
            }
        }
    }
}
}
