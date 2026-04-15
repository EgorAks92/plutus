package com.example.familyfinance.data.repository

import com.example.familyfinance.data.local.dao.PurchaseDao
import com.example.familyfinance.data.local.entity.PurchaseEntity
import com.example.familyfinance.domain.model.GroupExpenseTotal
import com.example.familyfinance.domain.model.Purchase
import com.example.familyfinance.domain.model.PurchaseHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PurchaseRepository(
    private val dao: PurchaseDao
) {
    suspend fun addPurchase(purchase: Purchase) {
        dao.insert(
            PurchaseEntity(
                productId = purchase.productId,
                groupId = purchase.groupId,
                price = purchase.price,
                quantity = purchase.quantity,
                date = purchase.date,
                note = purchase.note
            )
        )
    }

    fun observeHistory(
        groupId: Long?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<PurchaseHistory>> {
        return dao.observeHistory(groupId, startDate, endDate).map { items ->
            items.map {
                PurchaseHistory(
                    id = it.id,
                    productId = it.productId,
                    groupId = it.groupId,
                    productName = it.productName,
                    groupName = it.groupName,
                    price = it.price,
                    quantity = it.quantity,
                    date = it.date,
                    note = it.note,
                    total = it.total
                )
            }
        }
    }

    fun observeTotalForPeriod(startDate: Long, endDate: Long): Flow<Double> {
        return dao.observeTotalForPeriod(startDate, endDate)
    }

    fun observeGroupTotalsForPeriod(startDate: Long, endDate: Long): Flow<List<GroupExpenseTotal>> {
        return dao.observeGroupTotalsForPeriod(startDate, endDate).map { totals ->
            totals.map {
                GroupExpenseTotal(
                    groupId = it.groupId,
                    groupName = it.groupName,
                    total = it.total
                )
            }
        }
    }
}
