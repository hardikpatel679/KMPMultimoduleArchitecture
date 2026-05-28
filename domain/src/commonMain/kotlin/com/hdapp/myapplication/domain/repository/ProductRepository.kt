package com.hdapp.myapplication.domain.repository

import com.hdapp.myapplication.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(limit: Int, skip: Int): Result<List<Product>>
}
