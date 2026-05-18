package com.hdapp.myapplication.domain.repository

import com.hdapp.myapplication.domain.model.User

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<User>
}
