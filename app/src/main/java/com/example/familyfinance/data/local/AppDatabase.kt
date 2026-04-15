package com.example.familyfinance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.familyfinance.data.local.dao.ExpenseGroupDao
import com.example.familyfinance.data.local.dao.ProductDao
import com.example.familyfinance.data.local.dao.PurchaseDao
import com.example.familyfinance.data.local.entity.ExpenseGroupEntity
import com.example.familyfinance.data.local.entity.ProductEntity
import com.example.familyfinance.data.local.entity.PurchaseEntity

@Database(
    entities = [ExpenseGroupEntity::class, ProductEntity::class, PurchaseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseGroupDao(): ExpenseGroupDao
    abstract fun productDao(): ProductDao
    abstract fun purchaseDao(): PurchaseDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "family_finance.db"
            ).build()
        }
    }
}
