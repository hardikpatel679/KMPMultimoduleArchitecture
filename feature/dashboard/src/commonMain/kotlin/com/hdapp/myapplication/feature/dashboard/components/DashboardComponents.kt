package com.hdapp.myapplication.feature.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hdapp.myapplication.core.Dimens
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.domain.model.Product

@Composable
fun LanguageSwitcher(
    isArabic: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(end = Dimens.paddingSmall)
    ) {
        Text(
            text = if (isArabic) "العربية" else "English",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(Dimens.spacingSmall))
        Switch(
            checked = isArabic,
            onCheckedChange = onToggle,
            modifier = Modifier.testTag(TestTags.DASHBOARD_LANGUAGE_SWITCH)
        )
    }
}

@Composable
fun DashboardSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.headerHeight)
            .padding(horizontal = Dimens.paddingMedium),
        contentAlignment = Alignment.CenterEnd
    ) {
        AnimatedVisibility(
            visible = !isExpanded,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { onExpandChange(true) }) {
                    Icon(Icons.Default.Search, contentDescription = "Open Search")
                }
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandHorizontally(expandFrom = Alignment.End),
            exit = fadeOut() + shrinkHorizontally(shrinkTowards = Alignment.End)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .testTag(TestTags.DASHBOARD_SEARCH_FIELD),
                placeholder = { Text("Search products...") },
                leadingIcon = { 
                    Icon(Icons.Default.Search, contentDescription = null) 
                },
                trailingIcon = {
                    IconButton(onClick = { 
                        onExpandChange(false)
                        onQueryChange("")
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close Search")
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(Dimens.cornerRadiusLarge),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun ProductItemCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.elevationLow)
    ) {
        Row(
            modifier = Modifier
                .padding(Dimens.cardPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val placeholderPainter = rememberVectorPainter(Icons.Default.Image)
            
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(product.thumbnail.trim())
                    .crossfade(true)
                    .httpHeaders(
                        NetworkHeaders.Builder()
                            .set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                            .build()
                    )
                    .build(),
                contentDescription = product.title,
                modifier = Modifier
                    .size(Dimens.productImageSize)
                    .clip(RoundedCornerShape(Dimens.cornerRadiusSmall)),
                contentScale = ContentScale.Crop,
                placeholder = placeholderPainter,
                error = placeholderPainter
            )
            
            Spacer(modifier = Modifier.width(Dimens.spacingMedium))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
