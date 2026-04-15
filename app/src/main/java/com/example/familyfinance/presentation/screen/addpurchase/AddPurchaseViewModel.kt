package com.example.familyfinance.presentation.screen.addpurchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familyfinance.data.repository.ExpenseGroupRepository
import com.example.familyfinance.data.repository.ProductRepository
import com.example.familyfinance.data.repository.PurchaseRepository
import com.example.familyfinance.domain.model.Purchase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddPurchaseViewModel(
    private val purchaseRepository: PurchaseRepository,
    productRepository: ProductRepository,
    groupRepository: ExpenseGroupRepository
) : ViewModel() {

    val uiState: StateFlow<AddPurchaseUiState> = combine(
        productRepository.observeProducts(),
        groupRepository.observeGroups()
    ) { products, groups ->
        AddPurchaseUiState(products = products, groups = groups)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddPurchaseUiState()
    )

    fun addPurchase(
        productId: Long,
        groupId: Long,
        price: Double,
        quantity: Double,
        date: Long,
        note: String
    ) {
        if (productId <= 0 || groupId <= 0 || price < 0 || quantity <= 0) return
        viewModelScope.launch {
            purchaseRepository.addPurchase(
                Purchase(
                    id = 0,
                    productId = productId,
                    groupId = groupId,
                    price = price,
                    quantity = quantity,
                    date = date,
                    note = note.trim()
                )
            )
        }
    }
}

data class AddPurchaseUiState(
    val products: List<com.example.familyfinance.domain.model.Product> = emptyList(),
    val groups: List<com.example.familyfinance.domain.model.ExpenseGroup> = emptyList()
)
