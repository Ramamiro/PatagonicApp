package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTextField
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddProductScreen(viewModel: DataViewModel, navController: NavController) {
    var newProductName by remember { mutableStateOf("") }
    var newProductPricePerKg by remember { mutableStateOf("") }
    var newProductKgPerUnit by remember { mutableStateOf("") }

    Scaffold(topBar = {
        CustomTopBar("Add Product")
    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = paddingDefault)
        ) {
            Column(
                Modifier.fillMaxSize()
            ) {
                CustomTextField(
                    value = newProductName,
                    onValueChange = { newProductName = it },
                    icon = Icons.Default.Fastfood,
                    placeholder = "Product name"
                )

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomTextField(
                    value = newProductPricePerKg,
                    onValueChange = { newProductPricePerKg = it },
                    icon = Icons.Default.Money,
                    placeholder = "Price per Kg",
                    isNumeric = true
                )

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomTextField(
                    value = newProductKgPerUnit,
                    onValueChange = { newProductKgPerUnit = it },
                    icon = Icons.Default.Balance,
                    placeholder = "Kg per unit",
                    isNumeric = true
                )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomButton(value = "Add Product", onClick = {
                    try {
                        viewModel.addProduct(
                            Product(
                                productName = newProductName,
                                pricePerKg = newProductPricePerKg.toDouble(),
                                kgPerUnit = newProductKgPerUnit.toDouble()
                            )
                        )
                        navController.popBackStack()
                    } catch (_: Exception) {
                    }
                })
            }
        }
    }
}