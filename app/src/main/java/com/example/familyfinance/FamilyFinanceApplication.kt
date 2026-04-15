package com.example.familyfinance

import android.app.Application
import com.example.familyfinance.data.local.AppDatabase
import com.example.familyfinance.data.repository.ExpenseGroupRepository
import com.example.familyfinance.data.repository.ProductRepository
import com.example.familyfinance.data.repository.PurchaseRepository

class FamilyFinanceApplication : Application() {

    val appContainer: AppContainer by lazy {
        val database = AppDatabase.create(this)
        AppContainer(
            groupRepository = ExpenseGroupRepository(database.expenseGroupDao()),
            productRepository = ProductRepository(database.productDao()),
            purchaseRepository = PurchaseRepository(database.purchaseDao())
        )
    }
}

data class AppContainer(
    val groupRepository: ExpenseGroupRepository,
    val productRepository: ProductRepository,
    val purchaseRepository: PurchaseRepository
)
