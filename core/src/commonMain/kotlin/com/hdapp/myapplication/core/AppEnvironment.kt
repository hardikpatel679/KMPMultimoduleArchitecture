package com.hdapp.myapplication.core

import kotlin.native.concurrent.ThreadLocal

enum class AppEnvironment {
    DEV,
    MOCK,
    PROD
}

@ThreadLocal
object BuildContext {
    var environment: AppEnvironment = AppEnvironment.PROD
}
