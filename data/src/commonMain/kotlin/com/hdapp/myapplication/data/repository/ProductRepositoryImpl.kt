package com.hdapp.myapplication.data.repository

import com.hdapp.myapplication.core.NetworkConstants
import com.hdapp.myapplication.core.safeApiCall
import com.hdapp.myapplication.data.model.RemoteProductResponse
import com.hdapp.myapplication.data.model.toDomain
import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class ProductRepositoryImpl(
    private val httpClient: HttpClient
) : ProductRepository {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    override suspend fun getProducts(limit: Int, skip: Int): Result<List<Product>> {
        println("ProductRepositoryImpl: getProducts START")
        return safeApiCall {
            val response = httpClient.get(NetworkConstants.PRODUCTS_ENDPOINT) {
                parameter("limit", limit)
                parameter("skip", skip)
            }
            println("ProductRepositoryImpl: Response Status = ${response.status}")
            
            val bodyString = response.bodyAsText()
            val remoteResponse = json.decodeFromString<RemoteProductResponse>(bodyString)
            println("ProductRepositoryImpl: Parsed ${remoteResponse.products.size} products")

            remoteResponse.products.map { it.toDomain() }
        }
    }
}
