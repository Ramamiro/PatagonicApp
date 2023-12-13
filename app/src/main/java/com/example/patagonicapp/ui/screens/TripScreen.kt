package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun TripScreen(viewModel: DataViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White
            ) {}
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {},
            backgroundColor = MaterialTheme.colors.secondary)
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        viewModel.clientsState.clientsList.forEach() {
            LazyColumn() {
                items(viewModel.getClientOrdersById(it.clientId)) { item ->
                    val currentProduct = viewModel.getProductById(item.productId)
                    Row() {
                        Text(text = item.orderId.toString())
                        Text(text = it.clientName)
                        Text(text = currentProduct?.productName.toString())
                        Text(text = item.quantity.toString())
                    }
                }

            }
        }
    }
}
