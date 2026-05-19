package com.hdapp.myapplication.domain.repository

import com.hdapp.myapplication.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(limit: Int = 10, skip: Int = 0): Result<List<Product>>
}
