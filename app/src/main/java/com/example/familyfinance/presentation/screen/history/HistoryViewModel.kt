package com.example.familyfinance.presentation.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familyfinance.data.repository.ExpenseGroupRepository
import com.example.familyfinance.data.repository.PurchaseRepository
import com.example.familyfinance.domain.model.ExpenseGroup
import com.example.familyfinance.domain.model.PurchaseHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HistoryViewModel(
    purchaseRepository: PurchaseRepository,
    groupRepository: ExpenseGroupRepository
) : ViewModel() {

    private val filter = MutableStateFlow(HistoryFilter())

    private val purchasesFlow = filter.flatMapLatest {
        purchaseRepository.observeHistory(
            groupId = it.groupId,
            startDate = it.startDate,
            endDate = it.endDate
        )
    }

    val uiState: StateFlow<HistoryUiState> = combine(
        groupRepository.observeGroups(),
        purchasesFlow,
        filter
    ) { groups, purchases, activeFilter ->
        HistoryUiState(
            groups = groups,
            purchases = purchases,
            filter = activeFilter
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HistoryUiState()
    )

    fun updateGroupFilter(groupId: Long?) {
        filter.update { it.copy(groupId = groupId) }
    }

    fun updateStartDate(startDate: Long?) {
        filter.update { it.copy(startDate = startDate) }
    }

    fun updateEndDate(endDate: Long?) {
        filter.update { it.copy(endDate = endDate) }
    }
}

data class HistoryUiState(
    val groups: List<ExpenseGroup> = emptyList(),
    val purchases: List<PurchaseHistory> = emptyList(),
    val filter: HistoryFilter = HistoryFilter()
)

data class HistoryFilter(
    val groupId: Long? = null,
    val startDate: Long? = null,
    val endDate: Long? = null
)
