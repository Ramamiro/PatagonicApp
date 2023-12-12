package com.example.patagonicapp.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.models.Product
import com.example.roompractice.viewmodels.DataViewModel

data class OrderedProduct(
    val product: Product?,
    val amount: Double?
)

@Composable
fun AddOrderDialog(viewModel: DataViewModel, dismiss: () -> Unit) {

    var currentClientId by remember { mutableStateOf<Long?>(null) }
    var orderedProducts by remember { mutableStateOf(listOf(OrderedProduct(null, null))) }

    var newOrderedProductId by remember { mutableStateOf<Long?>(null) }
    var newOrderedProductAmount by remember { mutableStateOf<String?>(null) }

    var isProductsOptionsVisible by remember { mutableStateOf(false) }

    Surface(
        shape = shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Add New Order",
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ClientSelectionButton(
                viewModel = viewModel,
                currentClientId = currentClientId
            ) { selectedClientId ->
                currentClientId = selectedClientId
            }

            Spacer(modifier = Modifier.height(8.dp))

            orderedProducts.forEachIndexed() { index, orderedProduct ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OrderedProductItem(orderedProduct)
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    orderedProducts = orderedProducts.toMutableList().apply {
                                        removeAt(index)

                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProductSelectionButton(
                    viewModel = viewModel,
                    newOrderedProductId = newOrderedProductId,
                    newOrderedProductAmount = newOrderedProductAmount,
                    onSelectProduct = {
                        newOrderedProductId = it
                    },
                    onSelectAmount = {
                        newOrderedProductAmount = it
                    },
                    onAddOrderedProduct = {
                        orderedProducts = orderedProducts.toMutableList().apply {
                            add(OrderedProduct(viewModel.getProductById(newOrderedProductId), newOrderedProductAmount?.toDouble()))
                        }.toList()
                        newOrderedProductId = null
                        newOrderedProductAmount = ""
                    }
                )
            }
        }
    }
}


@Composable
fun ClientSelectionButton(
    viewModel: DataViewModel,
    currentClientId: Long?,
    onSelectClient: (selectedClientId: Long) -> Unit
) {

    var isClientsOptionsVisible by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = isClientsOptionsVisible,
        onDismissRequest = { isClientsOptionsVisible = false }) {
        viewModel.clientsState.clientsList.forEach() {
            DropdownMenuItem(onClick = {
                onSelectClient(it.clientId); isClientsOptionsVisible = false
            }) {
                Text(it.clientName)
            }
        }
    }
    Row {
        OutlinedButton(
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(
                1.dp,
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            ),
            onClick = {
                isClientsOptionsVisible = true
            }
        ) {
            key(currentClientId) {
                Text("Client: ${viewModel.getClientById(currentClientId)?.clientName ?: "null"}")
            }
        }
    }
}

@Composable
fun ProductSelectionButton(
    viewModel: DataViewModel,
    newOrderedProductId: Long?,
    newOrderedProductAmount: String?,
    onSelectProduct: (selectedProductId: Long?) -> Unit,
    onSelectAmount: (selectedAmount: String?) -> Unit,
    onAddOrderedProduct: () -> Unit
) {

    var isProductsOptionsVisible by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(0.6f)) {
        Box {
            OutlinedTextField(
                value = viewModel.getProductById(newOrderedProductId)?.productName ?: "",
                onValueChange = {},
                label = { Text("Product") },
                readOnly = true,
//                    modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) }
            )
            Spacer(modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(top = 6.dp)
                .clickable(
                    onClick = { isProductsOptionsVisible = true }
                ))
        }

        DropdownMenu(
            expanded = isProductsOptionsVisible,
            onDismissRequest = { isProductsOptionsVisible = false },
        ) {
            viewModel.productsState.productsList.forEach() {
                DropdownMenuItem(onClick = {
                    onSelectProduct(it.productId)
                    isProductsOptionsVisible = false
                }) {
                    Text(it.productName)
                }
            }
        }
    }


    Spacer(modifier = Modifier.width(8.dp))

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = newOrderedProductAmount ?: "",
            onValueChange = { onSelectAmount(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            ),
            label = { Text("Uds") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            Icons.Default.Done, null, modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clip(shapes.small)
                .clickable{onAddOrderedProduct()}
        )
    }

}

@Composable
fun OrderedProductItem(
    orderedProduct: OrderedProduct,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = orderedProduct.product?.productName ?: "null")

        Row(
            horizontalArrangement = Arrangement.End,
//            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = orderedProduct.amount?.toString() ?: "null")
        }
    }
}
