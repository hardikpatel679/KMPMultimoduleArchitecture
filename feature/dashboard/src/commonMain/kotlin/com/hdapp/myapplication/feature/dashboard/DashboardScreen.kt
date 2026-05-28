package com.hdapp.myapplication.feature.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import com.hdapp.myapplication.core.Dimens
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.core.components.EmptyStateView
import com.hdapp.myapplication.core.components.LoadingView
import com.hdapp.myapplication.core.strings
import com.hdapp.myapplication.feature.dashboard.components.DashboardSearchBar
import com.hdapp.myapplication.feature.dashboard.components.LanguageSwitcher
import com.hdapp.myapplication.feature.dashboard.components.ProductItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(DashboardIntent.LoadProducts)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToLogin -> onLogout()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        when(state.selectedTab) {
                            DashboardTab.Product -> strings.tabProduct
                            DashboardTab.Wealth -> strings.tabWealth
                            DashboardTab.Service -> strings.tabService
                            DashboardTab.Cart -> strings.tabCart
                        }
                    ) 
                },
                actions = {
                    LanguageSwitcher(
                        isArabic = state.isArabic,
                        onToggle = { viewModel.onIntent(DashboardIntent.ToggleLanguage) }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                DashboardTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = state.selectedTab == tab,
                        onClick = { viewModel.onIntent(DashboardIntent.SelectTab(tab)) },
                        label = { 
                            Text(
                                when(tab) {
                                    DashboardTab.Product -> strings.tabProduct
                                    DashboardTab.Wealth -> strings.tabWealth
                                    DashboardTab.Service -> strings.tabService
                                    DashboardTab.Cart -> strings.tabCart
                                }
                            ) 
                        },
                        icon = {
                            Icon(
                                imageVector = when (tab) {
                                    DashboardTab.Product -> Icons.AutoMirrored.Filled.List
                                    DashboardTab.Wealth -> Icons.Default.Star
                                    DashboardTab.Service -> Icons.Default.Settings
                                    DashboardTab.Cart -> Icons.Default.ShoppingCart
                                },
                                contentDescription = tab.name
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state.selectedTab) {
                DashboardTab.Product -> ProductTab(
                    state = state
                ) { onIntent -> viewModel.onIntent(onIntent) }
                DashboardTab.Wealth -> WealthTab()
                DashboardTab.Service -> ServiceTab()
                DashboardTab.Cart -> CartTab()
            }
        }
    }
}

@Composable
fun ProductTab(
    state: DashboardState,
    onIntent: (DashboardIntent) -> Unit
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            focusRequester.requestFocus()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DashboardSearchBar(
            query = state.searchQuery,
            onQueryChange = { onIntent(DashboardIntent.SearchProducts(it)) },
            isExpanded = isSearchExpanded,
            onExpandChange = { isSearchExpanded = it },
            focusRequester = focusRequester,
            title = strings.tabProduct
        )

        if (state.isLoading) {
            LoadingView()
        } else {
            val filteredProducts = remember(state.products, state.searchQuery) {
                if (state.searchQuery.isBlank()) {
                    state.products
                } else {
                    state.products.filter { 
                        it.title.contains(state.searchQuery, ignoreCase = true) ||
                        it.category.contains(state.searchQuery, ignoreCase = true)
                    }
                }
            }

            if (filteredProducts.isEmpty()) {
                EmptyStateView(message = "No products found")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(TestTags.DASHBOARD_PRODUCT_LIST),
                    contentPadding = PaddingValues(
                        bottom = Dimens.paddingMedium, 
                        start = Dimens.paddingMedium, 
                        end = Dimens.paddingMedium
                    ),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
                ) {
                    items(filteredProducts, key = { it.id }) { product ->
                        ProductItemCard(product = product)
                        
                        LaunchedEffect(filteredProducts.size) {
                            val isLastItem = product == filteredProducts.last()
                            val canLoadMore = state.hasMore && !state.isLoadingMore && state.searchQuery.isEmpty()
                            if (isLastItem && canLoadMore) {
                                onIntent(DashboardIntent.LoadMoreProducts)
                            }
                        }
                    }
                    
                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dimens.paddingMedium),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(Dimens.iconSizeSmall))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WealthTab() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(strings.tabWealth + " Content", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun ServiceTab() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(strings.tabService + " Content", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun CartTab() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(strings.tabCart + " Content", style = MaterialTheme.typography.headlineMedium)
    }
}
