package com.example.familyfinance.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.familyfinance.data.local.entity.ExpenseGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseGroupDao {

    @Query("SELECT * FROM expense_groups ORDER BY name ASC")
    fun observeAll(): Flow<List<ExpenseGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(group: ExpenseGroupEntity): Long

    @Update
    suspend fun update(group: ExpenseGroupEntity)

    @Delete
    suspend fun delete(group: ExpenseGroupEntity)
}
