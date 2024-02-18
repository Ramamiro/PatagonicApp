package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.Screens
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Debt
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.models.Payment
import com.example.patagonicapp.ui.customComponents.*
import com.example.patagonicapp.ui.dialogs.AddPaymentDialog
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.patagonicapp.viewmodels.PickerViewModel
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun ShowClientScreen(
    viewModel: DataViewModel,
    pickerViewModel: PickerViewModel,
    navController: NavController,
    clientId: Long
) {
    val activeTrip = viewModel.getActiveTrip()

    val client = viewModel.getClientById(clientId)!!

    val orders = viewModel.getOrdersByClientId(clientId = clientId, tripId = activeTrip?.id)

    val payments = viewModel.getPaymentsByClientId(clientId = clientId, tripId = activeTrip?.id)

    val debt = viewModel.getDebtByClientId(clientId = clientId)

    var isStatusMenuVisible by remember { mutableStateOf(false) }

    var isPaymentDialogVisible by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CustomTopBar("Client")
        }
    ) {
        Surface() {

        }
        Box(
            modifier = Modifier
                .padding(vertical = paddingDefault)
                .fillMaxSize()
        ) {
            Column(
            ) {

                CustomLabel(label = "CLIENT NAME")

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomText(text = client.clientName)

                Spacer(modifier = Modifier.height(paddingJump))

                CustomLabel(label = "LOCATION")

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomButton(
                    value = viewModel.getLocationById(client.locationId)!!.locationName,
                    onClick = { /*TODO*/ })

                Spacer(modifier = Modifier.height(paddingJump))

                CustomLabel(label = "CLIENT STATUS")

                Spacer(modifier = Modifier.height(paddingDivision))

                key(client) {
                    Column {
                        CustomButton(value = client.clientStatus.text,

                            color = if (client.clientStatus == ClientStatus.INACTIVE) {
                                Color.Black
                            } else {
                                client.clientStatus.color
                            },
                            onClick = {
                                if (client.clientStatus != ClientStatus.INACTIVE) {
                                    isStatusMenuVisible = true
                                }
                            })
                        StatusMenu(
                            client = client,
                            viewModel = viewModel,
                            isStatusMenuVisible = isStatusMenuVisible
                        ) {
                            isStatusMenuVisible = false
                        }
                    }
                }
                Spacer(modifier = Modifier.height(paddingJump))

                CustomLabel(label = "DEBT")

                Spacer(modifier = Modifier.height(paddingDivision))

                key(debt) {
                    if (debt != null) {
                        CustomButton(value = viewModel.getTripById(debt.tripId)!!.name, onClick = { /*TODO*/ })
                    }
                }

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomButton(
                    value = "",
                    onClick = {
                              
                    },
                    icon = Icons.Default.Add,
                    horizontalArrangement = Arrangement.Center
                )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomLabel(label = "ORDERS")

                Spacer(modifier = Modifier.height(paddingDivision))

                key(orders) {
                    if (orders.isNotEmpty()) {
                        CustomColumnList<Order>(
                            items = orders,
                            leadingText = {
                                val product = viewModel.getProductById(it.productId)!!
                                product.productName + "  x  " + it.quantity
                            },
                            endingText = { viewModel.formatMoney(it.total) },
                            endingIcon = {
                                Row {
                                    Spacer(Modifier.width(paddingDivision))
                                    Icon(
                                        Icons.Outlined.Delete,
                                        null,
                                        modifier = Modifier.clickable {
                                            viewModel.deleteOrderById(it.orderId, client.clientId)
                                        }
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(paddingDivision))

                        CustomText(
                            text = "Total: $" + orders.sumOf { it.total }.toString(),
                            horizontalArrangement = Arrangement.End
                        )
                    }
                }

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomButton(
                    value = "",
                    onClick = {
                        pickerViewModel.selectClient(clientId)
                        navController.navigate("${Screens.ADD_ORDER.route}")
                    },
                    icon = Icons.Default.Add,
                    horizontalArrangement = Arrangement.Center
                )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomLabel(label = "PAYMENTS")

                Spacer(modifier = Modifier.height(paddingDivision))

                key(payments) {
                    if (payments.isNotEmpty()) {
                        CustomColumnList<Payment>(
                            items = payments,
                            leadingText = { it.type.text },
                            endingText = { viewModel.formatMoney(it.amount) },
                            endingIcon = {
                                Row {
                                    Spacer(Modifier.width(paddingDivision))
                                    Icon(
                                        Icons.Outlined.Delete,
                                        null,
                                        modifier = Modifier.clickable {
                                            viewModel.deletePaymentById(
                                                it.id
                                            )
                                        })
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(paddingDivision))

                        CustomText(
                            text = "Total: " + viewModel.formatMoney(payments.sumOf { it.amount }),
                            horizontalArrangement = Arrangement.End
                        )
                    }
                }
                Spacer(modifier = Modifier.height(paddingDivision))

                CustomButton(
                    value = "",
                    onClick = {
                        isPaymentDialogVisible = true
                    },
                    icon = Icons.Default.Add,
                    horizontalArrangement = Arrangement.Center
                )
            }

            if (isPaymentDialogVisible) {
                Dialog(onDismissRequest = { isPaymentDialogVisible = false }) {
                    AddPaymentDialog(viewModel = viewModel, client = client) {
                        isPaymentDialogVisible = false
                    }
                }
            }
        }
    }
}

@Composable
fun StatusMenu(
    client: Client,
    viewModel: DataViewModel,
    isStatusMenuVisible: Boolean,
    dismiss: () -> Unit
) {
    DropdownMenu(
        expanded = isStatusMenuVisible,
        onDismissRequest = { dismiss() }
    ) {
        for (statusValue in ClientStatus.values()) {
            if (statusValue != ClientStatus.INACTIVE) {
                DropdownMenuItem(onClick = {
                    viewModel.updateClient(client.copy(clientStatus = statusValue))
                    dismiss()
                }) {
                    Text(text = statusValue.text)
                }
            }
        }
    }
}

