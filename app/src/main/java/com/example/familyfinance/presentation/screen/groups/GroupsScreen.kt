package com.example.familyfinance.presentation.screen.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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

@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var newGroupName by remember { mutableStateOf("") }
    var editingGroup by remember { mutableStateOf<ExpenseGroup?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = newGroupName,
            onValueChange = { newGroupName = it },
            label = { Text("New group") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                viewModel.addGroup(newGroupName)
                newGroupName = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add group")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.groups, key = { it.id }) { group ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(group.name)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { editingGroup = group }) { Text("Edit") }
                            Button(onClick = { viewModel.deleteGroup(group) }) { Text("Delete") }
                        }
                    }
                }
            }
        }
    }

    if (editingGroup != null) {
        var updatedName by remember(editingGroup!!.id) { mutableStateOf(editingGroup!!.name) }
        AlertDialog(
            onDismissRequest = { editingGroup = null },
            title = { Text("Edit group") },
            text = {
                OutlinedTextField(
                    value = updatedName,
                    onValueChange = { updatedName = it },
                    label = { Text("Group name") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateGroup(editingGroup!!.id, updatedName)
                    editingGroup = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { editingGroup = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
