package com.hdapp.myapplication.feature.login

import com.hdapp.myapplication.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidLoginViewModel @Inject constructor(
    loginUseCase: LoginUseCase
) : LoginViewModel(loginUseCase)
