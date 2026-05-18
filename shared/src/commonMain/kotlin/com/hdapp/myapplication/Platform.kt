package com.hdapp.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform