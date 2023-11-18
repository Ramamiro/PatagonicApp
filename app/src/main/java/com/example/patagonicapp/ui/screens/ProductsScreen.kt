package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.patagonicapp.models.Product
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun ProductsScreen(viewModel: DataViewModel) {
    LazyColumn() {
        items(viewModel.productsState.productsList) {
            ProductItem(item = it)
        }
    }
}

@Composable
fun ProductItem(item: Product) {
    Row() {
        Text(text = item.productId.toString())
        Text(text = item.productName)
    }
}