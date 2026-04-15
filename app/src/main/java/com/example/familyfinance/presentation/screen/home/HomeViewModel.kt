package com.example.familyfinance.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.familyfinance.data.repository.ExpenseGroupRepository
import com.example.familyfinance.data.repository.PurchaseRepository
import com.example.familyfinance.domain.model.GroupExpenseTotal
import com.example.familyfinance.presentation.common.currentMonthRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    purchaseRepository: PurchaseRepository,
    groupRepository: ExpenseGroupRepository
) : ViewModel() {

    private val monthRange = MutableStateFlow(currentMonthRange())

    val uiState: StateFlow<HomeUiState> = combine(
        monthRange,
        groupRepository.observeGroups(),
        purchaseRepository.observeTotalForPeriod(
            startDate = monthRange.value.first,
            endDate = monthRange.value.second
        ),
        purchaseRepository.observeGroupTotalsForPeriod(
            startDate = monthRange.value.first,
            endDate = monthRange.value.second
        )
    ) { _, groups, total, groupTotals ->
        val normalized = if (groupTotals.isNotEmpty()) {
            groupTotals
        } else {
            groups.map { GroupExpenseTotal(it.id, it.name, 0.0) }
        }
        HomeUiState(
            monthTotal = total,
            groupTotals = normalized
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )
}

data class HomeUiState(
    val monthTotal: Double = 0.0,
    val groupTotals: List<GroupExpenseTotal> = emptyList()
)
