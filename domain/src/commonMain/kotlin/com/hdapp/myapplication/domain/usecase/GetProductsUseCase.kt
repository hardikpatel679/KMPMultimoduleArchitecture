package com.hdapp.myapplication.domain.usecase

import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getProducts()
    }
}
