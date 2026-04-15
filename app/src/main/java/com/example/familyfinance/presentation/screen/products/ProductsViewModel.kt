package com.example.familyfinance.presentation.screen.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familyfinance.data.repository.ExpenseGroupRepository
import com.example.familyfinance.data.repository.ProductRepository
import com.example.familyfinance.domain.model.ExpenseGroup
import com.example.familyfinance.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val groupRepository: ExpenseGroupRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val uiState: StateFlow<ProductsUiState> = combine(
        productRepository.observeProducts(),
        groupRepository.observeGroups(),
        searchQuery
    ) { products, groups, query ->
        val filtered = if (query.isBlank()) {
            products
        } else {
            products.filter { it.name.contains(query, ignoreCase = true) }
        }
        ProductsUiState(
            products = filtered,
            groups = groups,
            searchQuery = query
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProductsUiState()
    )

    fun updateSearchQuery(query: String) {
        searchQuery.update { query }
    }

    fun addProduct(name: String, groupId: Long) {
        if (name.isBlank() || groupId <= 0) return
        viewModelScope.launch { productRepository.addProduct(name, groupId) }
    }

    fun updateProduct(id: Long, name: String, groupId: Long) {
        if (name.isBlank() || groupId <= 0) return
        viewModelScope.launch { productRepository.updateProduct(id, name, groupId) }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch { productRepository.deleteProduct(id) }
    }

    fun groupNameById(groupId: Long, groups: List<ExpenseGroup>): String {
        return groups.firstOrNull { it.id == groupId }?.name.orEmpty()
    }
}

data class ProductsUiState(
    val products: List<Product> = emptyList(),
    val groups: List<ExpenseGroup> = emptyList(),
    val searchQuery: String = ""
)
