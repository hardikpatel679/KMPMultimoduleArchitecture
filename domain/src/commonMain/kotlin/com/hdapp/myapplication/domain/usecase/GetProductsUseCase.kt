package com.hdapp.myapplication.domain.usecase

import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository

class GetProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(limit: Int = 10, skip: Int = 0): Result<List<Product>> {
        return repository.getProducts(limit, skip)
    }
}
