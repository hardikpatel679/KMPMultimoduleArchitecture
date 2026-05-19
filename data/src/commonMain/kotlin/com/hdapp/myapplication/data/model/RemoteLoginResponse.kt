package com.hdapp.myapplication.data.model

import com.hdapp.myapplication.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val accessToken: String,
    val refreshToken: String? = null
)
fun RemoteLoginResponse.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        accessToken = accessToken
    )
}
