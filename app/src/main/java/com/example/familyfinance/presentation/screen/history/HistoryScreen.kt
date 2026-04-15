package com.example.familyfinance.presentation.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.familyfinance.presentation.common.asCurrency
import com.example.familyfinance.presentation.common.asDateString
import com.example.familyfinance.presentation.common.DatePickerField
import com.example.familyfinance.presentation.screen.products.GroupDropdown

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GroupDropdown(
            groups = uiState.groups,
            selectedGroupId = uiState.filter.groupId,
            onSelectedGroup = { viewModel.updateGroupFilter(it) },
            label = "Filter group"
        )
        DatePickerField(
            selectedDate = uiState.filter.startDate ?: System.currentTimeMillis(),
            onDateSelected = { viewModel.updateStartDate(it) }
        )
        DatePickerField(
            selectedDate = uiState.filter.endDate ?: System.currentTimeMillis(),
            onDateSelected = { viewModel.updateEndDate(it) }
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.purchases, key = { it.id }) { purchase ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${purchase.productName} (${purchase.groupName})")
                        Text("${purchase.quantity} x ${purchase.price.asCurrency()} = ${purchase.total.asCurrency()}")
                        Text("Date: ${purchase.date.asDateString()}")
                        if (purchase.note.isNotBlank()) {
                            Text("Note: ${purchase.note}")
                        }
                    }
                }
            }
        }
    }
}
