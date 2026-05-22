package com.hdapp.myapplication.feature.dashboard

import com.hdapp.myapplication.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidDashboardViewModel @Inject constructor(
    getProductsUseCase: GetProductsUseCase
) : DashboardViewModel(getProductsUseCase)
