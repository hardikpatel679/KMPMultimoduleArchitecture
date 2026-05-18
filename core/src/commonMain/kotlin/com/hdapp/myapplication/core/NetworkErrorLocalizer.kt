package com.hdapp.myapplication.core

import androidx.compose.runtime.Composable

@Composable
fun NetworkError.localizedMessage(strings: AppStrings): String {
    return when (this) {
        is NetworkError.Network -> strings.errorNetwork
        is NetworkError.Serialization -> strings.errorSerialization
        is NetworkError.Unauthorized -> strings.errorUnauthorized
        is NetworkError.Forbidden -> strings.errorForbidden
        is NetworkError.NotFound -> strings.errorNotFound
        is NetworkError.ServerError -> strings.errorServer.replace("%d", code.toString())
        is NetworkError.Unknown -> errorMessage.ifBlank { strings.errorUnknown }
    }
}

fun NetworkError.getLocalizedMessage(strings: AppStrings): String {
    return when (this) {
        is NetworkError.Network -> strings.errorNetwork
        is NetworkError.Serialization -> strings.errorSerialization
        is NetworkError.Unauthorized -> strings.errorUnauthorized
        is NetworkError.Forbidden -> strings.errorForbidden
        is NetworkError.NotFound -> strings.errorNotFound
        is NetworkError.ServerError -> strings.errorServer.replace("%d", code.toString())
        is NetworkError.Unknown -> errorMessage.ifBlank { strings.errorUnknown }
    }
}
