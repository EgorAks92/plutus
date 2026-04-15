package com.example.familyfinance.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.familyfinance.AppContainer
import com.example.familyfinance.presentation.screen.addpurchase.AddPurchaseViewModel
import com.example.familyfinance.presentation.screen.groups.GroupsViewModel
import com.example.familyfinance.presentation.screen.history.HistoryViewModel
import com.example.familyfinance.presentation.screen.home.HomeViewModel
import com.example.familyfinance.presentation.screen.products.ProductsViewModel

class AppViewModelFactory(
    private val appContainer: AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(
                    purchaseRepository = appContainer.purchaseRepository,
                    groupRepository = appContainer.groupRepository
                ) as T
            }

            modelClass.isAssignableFrom(GroupsViewModel::class.java) -> {
                GroupsViewModel(appContainer.groupRepository) as T
            }

            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                ProductsViewModel(
                    productRepository = appContainer.productRepository,
                    groupRepository = appContainer.groupRepository
                ) as T
            }

            modelClass.isAssignableFrom(AddPurchaseViewModel::class.java) -> {
                AddPurchaseViewModel(
                    purchaseRepository = appContainer.purchaseRepository,
                    productRepository = appContainer.productRepository,
                    groupRepository = appContainer.groupRepository
                ) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(
                    purchaseRepository = appContainer.purchaseRepository,
                    groupRepository = appContainer.groupRepository
                ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
