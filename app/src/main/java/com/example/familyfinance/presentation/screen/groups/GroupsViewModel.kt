package com.example.familyfinance.presentation.screen.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familyfinance.data.repository.ExpenseGroupRepository
import com.example.familyfinance.domain.model.ExpenseGroup
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupsViewModel(
    private val repository: ExpenseGroupRepository
) : ViewModel() {

    val uiState: StateFlow<GroupsUiState> = repository.observeGroups()
        .map { groups -> GroupsUiState(groups = groups) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GroupsUiState()
        )

    fun addGroup(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch { repository.addGroup(name) }
    }

    fun updateGroup(id: Long, name: String) {
        if (name.isBlank()) return
        viewModelScope.launch { repository.updateGroup(id, name) }
    }

    fun deleteGroup(group: ExpenseGroup) {
        viewModelScope.launch { repository.deleteGroup(group) }
    }
}

data class GroupsUiState(
    val groups: List<ExpenseGroup> = emptyList()
)
