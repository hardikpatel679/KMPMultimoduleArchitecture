package com.hdapp.myapplication.feature.dashboard

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DashboardViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.state.value
        assertFalse(state.isArabic)
    }

    @Test
    fun `toggle language updates state correctly`() = runTest {
        viewModel.state.test {
            assertEquals(DashboardState(isArabic = false), awaitItem())

            viewModel.onIntent(DashboardIntent.ToggleLanguage)
            assertEquals(DashboardState(isArabic = true), awaitItem())

            viewModel.onIntent(DashboardIntent.ToggleLanguage)
            assertEquals(DashboardState(isArabic = false), awaitItem())
        }
    }

    @Test
    fun `select tab updates state correctly`() = runTest {
        viewModel.state.test {
            assertEquals(DashboardState(selectedTab = DashboardTab.Product), awaitItem())

            viewModel.onIntent(DashboardIntent.SelectTab(DashboardTab.Wealth))
            assertEquals(DashboardState(selectedTab = DashboardTab.Wealth), awaitItem())

            viewModel.onIntent(DashboardIntent.SelectTab(DashboardTab.Cart))
            assertEquals(DashboardState(selectedTab = DashboardTab.Cart), awaitItem())
        }
    }

    @Test
    fun `logout emits NavigateToLogin effect`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(DashboardIntent.Logout)
            assertEquals(DashboardEffect.NavigateToLogin, awaitItem())
        }
    }
}
