package com.example.familyfinance.data.repository

import com.example.familyfinance.data.local.dao.ProductDao
import com.example.familyfinance.data.local.entity.ProductEntity
import com.example.familyfinance.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val dao: ProductDao
) {
    fun observeProducts(): Flow<List<Product>> = dao.observeAll().map { products ->
        products.map { Product(id = it.id, name = it.name, groupId = it.groupId) }
    }

    suspend fun addProduct(name: String, groupId: Long) {
        dao.insert(ProductEntity(name = name.trim(), groupId = groupId))
    }

    suspend fun updateProduct(id: Long, name: String, groupId: Long) {
        dao.update(ProductEntity(id = id, name = name.trim(), groupId = groupId))
    }

    suspend fun deleteProduct(id: Long) {
        dao.deleteById(id)
    }
}
