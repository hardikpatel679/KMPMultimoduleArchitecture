package com.hdapp.myapplication.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Data class representing all translatable strings in the app.
 */
data class AppStrings(
    val dashboardTitle: String,
    val dashboardWelcome: String,
    val appLogout: String,
    val loginWelcome: String,
    val loginSubtitle: String,
    val loginUsernameLabel: String,
    val loginUsernamePlaceholder: String,
    val loginPasswordLabel: String,
    val loginPasswordPlaceholder: String,
    val loginShowPassword: String,
    val loginHidePassword: String,
    val loginButton: String,
    val loginForgotPassword: String,
    val loginNoAccount: String,
    val loginSignUp: String,
    // Error messages
    val errorNetwork: String,
    val errorSerialization: String,
    val errorUnauthorized: String,
    val errorForbidden: String,
    val errorNotFound: String,
    val errorServer: String, // Expecting placeholder like %d
    val errorUnknown: String,
)

/**
 * Default strings (English)
 */
@Composable
fun defaultStrings() = AppStrings(
    dashboardTitle = "Dashboard",
    dashboardWelcome = "Welcome to the Dashboard!",
    appLogout = "Logout",
    loginWelcome = "Welcome Back",
    loginSubtitle = "Login to your account",
    loginUsernameLabel = "Username",
    loginUsernamePlaceholder = "Enter your username",
    loginPasswordLabel = "Password",
    loginPasswordPlaceholder = "Enter your password",
    loginShowPassword = "Show",
    loginHidePassword = "Hide",
    loginButton = "Login",
    loginForgotPassword = "Forgot Password?",
    loginNoAccount = "Don't have an account?",
    loginSignUp = "Sign Up",
    errorNetwork = "No internet connection",
    errorSerialization = "Error parsing server response",
    errorUnauthorized = "Unauthorized access",
    errorForbidden = "Access forbidden",
    errorNotFound = "Resource not found",
    errorServer = "Server error occurred (Code: %d)",
    errorUnknown = "An unexpected error occurred"
)

/**
 * Arabic strings
 */
@Composable
fun arabicStrings() = AppStrings(
    dashboardTitle = "لوحة القيادة",
    dashboardWelcome = "مرحباً بك في لوحة القيادة!",
    appLogout = "تسجيل الخروج",
    loginWelcome = "مرحباً بعودتك",
    loginSubtitle = "قم بتسجيل الدخول إلى حسابك",
    // Keep username and password in English
    loginUsernameLabel = "Username",
    loginUsernamePlaceholder = "Enter your username",
    loginPasswordLabel = "Password",
    loginPasswordPlaceholder = "Enter your password",
    loginShowPassword = "Show",
    loginHidePassword = "Hide",
    loginButton = "تسجيل الدخول",
    loginForgotPassword = "هل نسيت كلمة المرور؟",
    loginNoAccount = "ليس لديك حساب؟",
    loginSignUp = "سجل الآن",
    errorNetwork = "لا يوجد اتصال بالإنترنت",
    errorSerialization = "خطأ في تحليل استجابة الخادم",
    errorUnauthorized = "وصول غير مصرح به",
    errorForbidden = "الوصول ممنوع",
    errorNotFound = "المورد غير موجود",
    errorServer = "حدث خطأ في الخادم (الكود: %d)",
    errorUnknown = "حدث خطأ غير متوقع"
)

val LocalStrings = staticCompositionLocalOf {
    AppStrings(
        dashboardTitle = "Dashboard",
        dashboardWelcome = "Welcome",
        appLogout = "Logout",
        loginWelcome = "Welcome Back",
        loginSubtitle = "Login to your account",
        loginUsernameLabel = "Username",
        loginUsernamePlaceholder = "Enter your username",
        loginPasswordLabel = "Password",
        loginPasswordPlaceholder = "Enter your password",
        loginShowPassword = "Show",
        loginHidePassword = "Hide",
        loginButton = "Login",
        loginForgotPassword = "Forgot Password?",
        loginNoAccount = "Don't have an account?",
        loginSignUp = "Sign Up",
        errorNetwork = "No internet connection",
        errorSerialization = "Error parsing server response",
        errorUnauthorized = "Unauthorized access",
        errorForbidden = "Access forbidden",
        errorNotFound = "Resource not found",
        errorServer = "Server error occurred (Code: %d)",
        errorUnknown = "An unexpected error occurred"
    )
}

@Composable
fun ProvideAppStrings(
    isArabic: Boolean,
    content: @Composable () -> Unit
) {
    val strings = if (isArabic) arabicStrings() else defaultStrings()
    CompositionLocalProvider(LocalStrings provides strings) {
        content()
    }
}

/**
 * Helper to access strings easily in Composables
 */
val strings: AppStrings
    @Composable
    @ReadOnlyComposable
    get() = LocalStrings.current
