package com.hdapp.myapplication.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.platform.testTag
import coil3.compose.AsyncImage
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.core.strings
import com.hdapp.myapplication.domain.model.Product
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (state.isArabic) "العربية" else "English",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = state.isArabic,
                            onCheckedChange = { viewModel.onIntent(DashboardIntent.ToggleLanguage) },
                            modifier = Modifier.testTag(TestTags.DASHBOARD_LANGUAGE_SWITCH)
                        )
                    }
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
                    state = state,
                    onCategorySelected = { viewModel.onIntent(DashboardIntent.SelectCategory(it)) },
                    onLogout = { viewModel.onIntent(DashboardIntent.Logout) }
                )
                DashboardTab.Wealth -> WealthTab()
                DashboardTab.Service -> ServiceTab()
                DashboardTab.Cart -> CartTab()
            }
            
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            
            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ProductTab(
    state: DashboardState,
    onCategorySelected: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Horizontal Category List
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.categories) { category ->
                val isSelected = state.selectedCategory == category
                Surface(
                    onClick = { onCategorySelected(category) },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 2.dp
                ) {
                    Text(
                        text = category.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Vertical Product List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.filteredProducts) { product ->
                ProductItem(product = product)
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().testTag(TestTags.DASHBOARD_LOGOUT_BUTTON)
                ) {
                    Text(strings.appLogout)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
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
