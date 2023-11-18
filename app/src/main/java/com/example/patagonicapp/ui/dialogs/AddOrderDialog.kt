package com.example.patagonicapp.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.patagonicapp.models.Client
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddOrderDialog(viewModel: DataViewModel, dismiss: () -> Unit) {
    var showClientsOptions by remember { mutableStateOf(false) }
    var showProductOptions by remember { mutableStateOf(false) }
    var selectedClientId by remember { mutableStateOf<Long?>(null) }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Add New Order",
                    style = MaterialTheme.typography.h6
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                val selectedClient = viewModel.getClientById(selectedClientId)
                Text("Client: ${selectedClient?.clientName ?: ""}",
                    modifier = Modifier.clickable { showClientsOptions = true })
                DropdownMenu(expanded = showClientsOptions, onDismissRequest = { showClientsOptions=false }) {
                    viewModel.clientsState.clientsList.forEach(){
                        DropdownMenuItem(onClick = {selectedClientId = it.clientId}) {
                            Text(it.clientName)
                        }
                    }
                }
            }
//            OutlinedTextField(
//                value = newProductName,
//                onValueChange = { newProductName = it },
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Text
//                )
//            )
        }
    }
}
