package com.hdapp.myapplication.core

sealed class NetworkError(message: String? = null) : Throwable(message) {
    class Network : NetworkError("No internet connection")
    class Serialization : NetworkError("Error parsing server response")
    class Unauthorized : NetworkError("Unauthorized access")
    class Forbidden : NetworkError("Access forbidden")
    class NotFound : NetworkError("Resource not found")
    class ServerError(val code: Int) : NetworkError("Server error occurred (Code: $code)")
    class Unknown(val errorMessage: String) : NetworkError(errorMessage)
}
