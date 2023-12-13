package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun TripScreen(viewModel: DataViewModel) {
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
