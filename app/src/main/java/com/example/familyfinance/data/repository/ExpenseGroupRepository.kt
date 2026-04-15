package com.example.familyfinance.data.repository

import com.example.familyfinance.data.local.dao.ExpenseGroupDao
import com.example.familyfinance.data.local.entity.ExpenseGroupEntity
import com.example.familyfinance.domain.model.ExpenseGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseGroupRepository(
    private val dao: ExpenseGroupDao
) {
    fun observeGroups(): Flow<List<ExpenseGroup>> = dao.observeAll().map { groups ->
        groups.map { ExpenseGroup(id = it.id, name = it.name) }
    }

    suspend fun addGroup(name: String) {
        dao.insert(ExpenseGroupEntity(name = name.trim()))
    }

    suspend fun updateGroup(id: Long, name: String) {
        dao.update(ExpenseGroupEntity(id = id, name = name.trim()))
    }

    suspend fun deleteGroup(group: ExpenseGroup) {
        dao.delete(ExpenseGroupEntity(id = group.id, name = group.name))
    }
}
