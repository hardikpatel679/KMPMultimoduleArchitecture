package com.hdapp.myapplication.data.repository

import com.hdapp.myapplication.core.safeApiCall
import com.hdapp.myapplication.data.model.RemoteProductResponse
import com.hdapp.myapplication.data.model.toDomain
import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductRepositoryImpl(
    private val httpClient: HttpClient
) : ProductRepository {
    override suspend fun getProducts(limit: Int, skip: Int): Result<List<Product>> {
        return safeApiCall {
            val response: RemoteProductResponse = httpClient.get("https://dummyjson.com/products") {
                parameter("limit", limit)
                parameter("skip", skip)
            }.body()
            response.products.map { it.toDomain() }
        }
    }
}
