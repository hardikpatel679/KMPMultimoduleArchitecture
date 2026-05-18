package com.hdapp.myapplication.feature.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hdapp.myapplication.core.Dimens
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.core.getLocalizedMessage
import com.hdapp.myapplication.core.localizedMessage
import com.hdapp.myapplication.core.strings
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.platform.testTag
import org.jetbrains.compose.resources.painterResource
import myapplication.core.generated.resources.Res
import myapplication.core.generated.resources.ic_logo

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val currentStrings = strings
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
        Image(
            painter = painterResource(Res.drawable.ic_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(Dimens.logoSize)
                .padding(Dimens.paddingSmall)
                .testTag(TestTags.LOGIN_LOGO)
        )

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
