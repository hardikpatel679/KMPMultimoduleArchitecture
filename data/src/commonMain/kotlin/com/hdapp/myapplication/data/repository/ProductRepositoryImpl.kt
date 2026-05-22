package com.hdapp.myapplication.data.repository

import com.hdapp.myapplication.core.safeApiCall
import com.hdapp.myapplication.data.model.RemoteProductResponse
import com.hdapp.myapplication.data.model.toDomain
import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductRepositoryImpl(
    private val httpClient: HttpClient
) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return safeApiCall {
            val response: RemoteProductResponse = httpClient.get("https://dummyjson.com/products").body()
            response.products.map { it.toDomain() }
        }
    }
}
