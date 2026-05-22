package com.hdapp.myapplication.core

import kotlin.native.concurrent.ThreadLocal

enum class AppEnvironment {
    DEV,
    MOCK,
    PROD
}

@ThreadLocal
object AppBuildContext {
    var environment: AppEnvironment = AppEnvironment.PROD
}
