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
import com.example.patagonicapp.models.Product
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddProductDialog(viewModel: DataViewModel, dismiss: () -> Unit) {

    var newProductName by remember { mutableStateOf("") }
    var newProductPrice by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Add New Product",
                    style = MaterialTheme.typography.h6
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newProductName,
                onValueChange = { newProductName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newProductPrice.toString(),
                onValueChange = { newProductPrice = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.addProduct(
                        Product(
                            productName = newProductName,
                            productPrice = newProductPrice.toDouble()
                        )
                    )
                    dismiss()
                },
            ) {
                Text("Add Product")
            }
        }
    }
}