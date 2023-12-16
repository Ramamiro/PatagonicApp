package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.patagonicapp.Screens
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomSpinner
import com.example.patagonicapp.ui.customComponents.CustomTextField
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddOrderScreen(
    viewModel: DataViewModel,
    navController: NavController,
    params: Map<String, Long?>
) {
    var clientId by remember {
        mutableStateOf(params["selectedClientId"])
    }

    var productId by remember {
        mutableStateOf<Long?>(null)
    }

    var amount by remember { mutableStateOf("") }

    val product = viewModel.getProductById(productId)
    val client = viewModel.getClientById(clientId)

    Scaffold(
        topBar = {
            CustomTopBar("Add Order")
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = paddingDefault)
        )
        {
            Column(
                Modifier.fillMaxSize()
            ) {
                CustomButton(
                    value = client?.clientName ?: "Client name",
                    onClick = {
                    },

                    )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomSpinner(
                    options = viewModel.productsState.productsList,
                    value = product?.productName ?: "",
                    onSelection = { productId = it.productId },
                    onDisplay = { it.productName },
                    placeholder = "Product name"
                )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = "Amount",
                    isNumeric = true
                )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomButton(value = "Add order", onClick = {
                    if (clientId != null && productId != null) {
                        viewModel.addOrder(
                            Order(
                                clientId = clientId!!,
                                productId = productId!!,
                                quantity = if (amount.toInt() == 0) {
                                    1
                                } else {
                                    amount.toInt()
                                }
                            )
                        )
                        navController.popBackStack()
                    }
                })
            }
        }
    }
}