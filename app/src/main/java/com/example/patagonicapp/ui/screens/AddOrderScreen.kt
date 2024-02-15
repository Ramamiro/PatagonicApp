package com.example.patagonicapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.Screens
import com.example.patagonicapp.TYPE
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTextField
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel
import com.example.patagonicapp.viewmodels.PickerViewModel

@Composable
fun AddOrderScreen(
    viewModel: DataViewModel,
    navController: NavController,
    pickerViewModel: PickerViewModel
) {

    fun popBack() {
        pickerViewModel.clear()
        navController.popBackStack()
    }

    BackHandler() {
        popBack()
    }

    val client = viewModel.getClientById(pickerViewModel.selectedClientId.value)

    val product = viewModel.getProductById(pickerViewModel.selectedProductId.value)

    var quantity by rememberSaveable {
        mutableStateOf("")
    }

    key(client, product) {
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
                        value = client?.clientName ?: "Client",
                        onClick = {
                            navController.navigate("${Screens.PICKER}/${TYPE.CLIENT.name}")
                        },
                        icon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(paddingJump))

                    CustomButton(
                        value = product?.productName ?: "Product",
                        onClick = {
                            navController.navigate("${Screens.PICKER}/${TYPE.PRODUCT.name}")
                        },
                        icon = Icons.Default.Label
                    )

                    Spacer(modifier = Modifier.height(paddingJump))

                    CustomTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        placeholder = "Quantity",
                        isNumeric = true,
                        icon = Icons.Default.LocalOffer
                    )

                    Spacer(modifier = Modifier.height(paddingJump))

                    CustomButton(value = "Add order", onClick = {
                        val activeTrip = viewModel.getActiveTrip()
                        if (activeTrip != null) {
                            if (client != null && product != null && quantity.toInt() != 0) {
                                try {
                                    viewModel.addOrder(
                                        Order(
                                            clientId = client.clientId,
                                            productId = product.productId,
                                            quantity = quantity.toInt(),
                                            total = product.kgPerUnit * product.pricePerKg * quantity.toInt(),
                                            weight = product.kgPerUnit * quantity.toInt(),
                                            tripId = activeTrip.id
                                        ),
                                        clientId = client.clientId
                                    )
                                    popBack()
                                } catch (_: Exception) {
                                }
                            }
                        }
                    }
                    )
                }
            }
        }
    }
}