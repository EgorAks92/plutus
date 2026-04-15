package com.example.familyfinance.presentation.screen.addpurchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.familyfinance.domain.model.ExpenseGroup
import com.example.familyfinance.domain.model.Product
import com.example.familyfinance.presentation.common.DatePickerField
import com.example.familyfinance.presentation.screen.products.GroupDropdown

@Composable
fun AddPurchaseScreen(
    viewModel: AddPurchaseViewModel,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var productId by remember { mutableStateOf<Long?>(null) }
    var groupId by remember { mutableStateOf<Long?>(null) }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductDropdown(
            products = uiState.products,
            selectedProductId = productId,
            onSelected = { productId = it }
        )
        GroupDropdown(
            groups = uiState.groups,
            selectedGroupId = groupId,
            onSelectedGroup = { groupId = it },
            label = "Expense group"
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        DatePickerField(selectedDate = date, onDateSelected = { date = it })
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.addPurchase(
                    productId = productId ?: -1,
                    groupId = groupId ?: -1,
                    price = price.toDoubleOrNull() ?: -1.0,
                    quantity = quantity.toDoubleOrNull() ?: -1.0,
                    date = date,
                    note = note
                )
                price = ""
                quantity = "1"
                note = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save purchase")
        }
    }
}

@Composable
private fun ProductDropdown(
    products: List<Product>,
    selectedProductId: Long?,
    onSelected: (Long) -> Unit
) {
    val asGroups: List<ExpenseGroup> = products.map { ExpenseGroup(it.id, it.name) }
    GroupDropdown(
        groups = asGroups,
        selectedGroupId = selectedProductId,
        onSelectedGroup = onSelected,
        label = "Product"
    )
}
