package com.example.patagonicapp.ui.dialogs

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
fun AddClientDialog(viewModel: DataViewModel, dismiss: () -> Unit) {

    var newClientName by remember { mutableStateOf("") }
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
            Text(
                text = "Add New Client",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newClientName,
                onValueChange = { newClientName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.addClient(Client(clientName = newClientName, clientBusiness = ""))
                    dismiss()
                          },
            ) {
                Text("Add Client")
            }
        }
    }
}