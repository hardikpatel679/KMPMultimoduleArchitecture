package com.hdapp.myapplication.core

import io.ktor.client.plugins.*
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        println("SafeApiCall: Caught throwable: ${e::class.simpleName} - ${e.message}")
        e.printStackTrace()
        if (e is CancellationException) throw e
        
        val error = if (e is Exception) mapToNetworkError(e) else NetworkError.Unknown(e.message ?: "Fatal Error")
        Result.failure(error)
    }
}

private fun mapToNetworkError(e: Exception): NetworkError {
    return when (e) {
        is ClientRequestException -> {
            when (e.response.status.value) {
                401 -> NetworkError.Unauthorized()
                403 -> NetworkError.Forbidden()
                404 -> NetworkError.NotFound()
                else -> NetworkError.Unknown(e.message)
            }
        }
        is ServerResponseException -> NetworkError.ServerError(e.response.status.value)
        is ResponseException -> NetworkError.Unknown(e.message ?: "")
        is SerializationException -> NetworkError.Serialization()
        else -> {
            // Using a generic check for IO-related issues to avoid deprecated IOException
            val isNetworkIssue = e is kotlinx.io.IOException || 
                                e.message?.contains("Unable to resolve host", ignoreCase = true) == true ||
                                e.message?.contains("timeout", ignoreCase = true) == true
            
            if (isNetworkIssue) {
                NetworkError.Network()
            } else {
                NetworkError.Unknown(e.message ?: "")
            }
        }
    }
}
