package com.example.familyfinance.presentation.screen.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var name by remember { mutableStateOf("") }
    var selectedGroupId by remember { mutableStateOf<Long?>(null) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            label = { Text("Search products") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product name") },
            modifier = Modifier.fillMaxWidth()
        )

        GroupDropdown(
            groups = uiState.groups,
            selectedGroupId = selectedGroupId,
            onSelectedGroup = { selectedGroupId = it },
            label = "Group"
        )

        Button(
            onClick = {
                viewModel.addProduct(name = name, groupId = selectedGroupId ?: -1)
                name = ""
                selectedGroupId = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add product")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.products, key = { it.id }) { product ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(product.name)
                            Text(viewModel.groupNameById(product.groupId, uiState.groups))
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { editingProduct = product }) { Text("Edit") }
                            Button(onClick = { viewModel.deleteProduct(product.id) }) { Text("Delete") }
                        }
                    }
                }
            }
        }
    }

    editingProduct?.let { product ->
        EditProductDialog(
            product = product,
            groups = uiState.groups,
            onDismiss = { editingProduct = null },
            onSave = { updatedName, updatedGroupId ->
                viewModel.updateProduct(product.id, updatedName, updatedGroupId)
                editingProduct = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDropdown(
    groups: List<ExpenseGroup>,
    selectedGroupId: Long?,
    onSelectedGroup: (Long) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedLabel = groups.firstOrNull { it.id == selectedGroupId }?.name.orEmpty()

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            groups.forEach { group ->
                DropdownMenuItem(
                    text = { Text(group.name) },
                    onClick = {
                        onSelectedGroup(group.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun EditProductDialog(
    product: Product,
    groups: List<ExpenseGroup>,
    onDismiss: () -> Unit,
    onSave: (name: String, groupId: Long) -> Unit
) {
    var name by remember(product.id) { mutableStateOf(product.name) }
    var groupId by remember(product.id) { mutableStateOf(product.groupId) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit product") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                GroupDropdown(
                    groups = groups,
                    selectedGroupId = groupId,
                    onSelectedGroup = { groupId = it },
                    label = "Group"
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(name, groupId) }) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
