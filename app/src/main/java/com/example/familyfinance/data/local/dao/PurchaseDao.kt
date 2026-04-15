package com.example.familyfinance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.familyfinance.data.local.entity.PurchaseEntity
import com.example.familyfinance.data.local.model.GroupTotal
import com.example.familyfinance.data.local.model.PurchaseHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(purchase: PurchaseEntity): Long

    @Query(
        """
        SELECT p.id, p.productId, p.groupId, pr.name AS productName, g.name AS groupName, p.price, p.quantity, p.date, p.note
        FROM purchases p
        INNER JOIN products pr ON p.productId = pr.id
        INNER JOIN expense_groups g ON p.groupId = g.id
        WHERE (:groupId IS NULL OR p.groupId = :groupId)
        AND (:startDate IS NULL OR p.date >= :startDate)
        AND (:endDate IS NULL OR p.date <= :endDate)
        ORDER BY p.date DESC
        """
    )
    fun observeHistory(
        groupId: Long?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<PurchaseHistoryItem>>

    @Query(
        """
        SELECT COALESCE(SUM(price * quantity), 0)
        FROM purchases
        WHERE date BETWEEN :startDate AND :endDate
        """
    )
    fun observeTotalForPeriod(startDate: Long, endDate: Long): Flow<Double>

    @Query(
        """
        SELECT g.id AS groupId, g.name AS groupName, COALESCE(SUM(p.price * p.quantity), 0) AS total
        FROM expense_groups g
        LEFT JOIN purchases p ON p.groupId = g.id AND p.date BETWEEN :startDate AND :endDate
        GROUP BY g.id, g.name
        ORDER BY g.name ASC
        """
    )
    fun observeGroupTotalsForPeriod(startDate: Long, endDate: Long): Flow<List<GroupTotal>>
}
