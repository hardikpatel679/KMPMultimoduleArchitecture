package com.hdapp.myapplication.feature.dashboard

import app.cash.turbine.test
import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository
import com.hdapp.myapplication.domain.usecase.GetProductsUseCase
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
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var fakeRepository: FakeProductRepository

    private class FakeProductRepository : ProductRepository {
        var products = listOf<Product>()
        var result: Result<List<Product>> = Result.success(emptyList())

        override suspend fun getProducts(limit: Int, skip: Int): Result<List<Product>> {
            return result
        }
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeProductRepository()
        getProductsUseCase = GetProductsUseCase(fakeRepository)
        viewModel = DashboardViewModel(getProductsUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.state.value
        assertFalse(state.isArabic)
        assertTrue(state.products.isEmpty())
        assertFalse(state.isLoading)
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
    fun `logout emits NavigateToLogin effect`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(DashboardIntent.Logout)
            assertEquals(DashboardEffect.NavigateToLogin, awaitItem())
        }
    }

    @Test
    fun `load products success updates state correctly`() = runTest {
        val mockProducts = listOf(
            Product(1, "Product 1", "Desc 1", 10.0, "", "Cat 1"),
            Product(2, "Product 2", "Desc 2", 20.0, "", "Cat 2")
        )
        fakeRepository.result = Result.success(mockProducts)

        viewModel.state.test {
            assertEquals(DashboardState(), awaitItem())

            viewModel.onIntent(DashboardIntent.LoadProducts)
            
            assertEquals(DashboardState(isLoading = true, hasMore = true), awaitItem())
            val loadedState = awaitItem()
            assertEquals(mockProducts, loadedState.products)
            assertFalse(loadedState.isLoading)
            assertFalse(loadedState.hasMore) // Because size < 10
        }
    }

    @Test
    fun `search products updates query in state`() = runTest {
        viewModel.state.test {
            assertEquals(DashboardState(), awaitItem())

            viewModel.onIntent(DashboardIntent.SearchProducts("phone"))
            assertEquals(DashboardState(searchQuery = "phone"), awaitItem())
        }
    }

    @Test
    fun `load more products appends to existing list`() = runTest {
        val initialProducts = List(10) { Product(it, "P $it", "D", 1.0, "", "C") }
        fakeRepository.result = Result.success(initialProducts)
        
        viewModel.onIntent(DashboardIntent.LoadProducts)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val moreProducts = listOf(Product(10, "P 10", "D", 1.0, "", "C"))
        fakeRepository.result = Result.success(moreProducts)

        viewModel.state.test {
            val currentState = awaitItem()
            assertEquals(10, currentState.products.size)
            assertTrue(currentState.hasMore)

            viewModel.onIntent(DashboardIntent.LoadMoreProducts)
            
            assertEquals(currentState.copy(isLoadingMore = true), awaitItem())
            val finalState = awaitItem()
            assertEquals(11, finalState.products.size)
            assertFalse(finalState.isLoadingMore)
            assertFalse(finalState.hasMore)
        }
    }
}
