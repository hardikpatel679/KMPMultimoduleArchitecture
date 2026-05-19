package com.hdapp.myapplication.data.model

import com.hdapp.myapplication.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class RemoteProductResponse(
    val products: List<RemoteProduct>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

@Serializable
data class RemoteProduct(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String? = null,
    val brand: String? = null,
    val rating: Double? = null,
    val stock: Int? = null
)

fun RemoteProduct.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail ?: "",
        category = category
    )
}
