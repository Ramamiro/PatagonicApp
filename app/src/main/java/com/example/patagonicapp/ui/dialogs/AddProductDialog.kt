package com.example.patagonicapp.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.models.Product
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddProductDialog(viewModel: DataViewModel, dismiss: () -> Unit) {

    var newProductName by remember { mutableStateOf("") }
    var newProductPricePerKg by remember { mutableStateOf("") }
    var newProductKgPerUnit by remember { mutableStateOf("") }

    Surface(
        shape= shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "New Product",
                    style = MaterialTheme.typography.h6,
                    color= colors.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newProductName,
                onValueChange = { newProductName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("Product name") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                OutlinedTextField(
                    value = newProductPricePerKg.toString(),
                    onValueChange = { newProductPricePerKg = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal
                    ),
                    label = { Text("Price") }
                    ,
                    modifier = Modifier.width(170.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    value = newProductKgPerUnit.toString(),
                    onValueChange = { newProductKgPerUnit = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal
                    ),
                    label = { Text("Kg") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()) {
                Button(
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        try {
                            viewModel.addProduct(
                                Product(
                                    productName = newProductName,
                                    pricePerKg = newProductPricePerKg.toDouble(),
                                    kgPerUnit = newProductKgPerUnit.toDouble()
                                )
                            )
                            dismiss()
                        } catch (_: Exception) {
                        }
                    },
                ) {
                    Text("Add")
                }
            }
        }
    }
}