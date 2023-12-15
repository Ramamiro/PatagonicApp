package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.patagonicapp.Screens
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun ProductsScreen(viewModel: DataViewModel, navController: NavController) {
    Scaffold(
        topBar = { CustomTopBar(title = Screens.PRODUCTS.route) }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = paddingDefault)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {

                LazyColumn() {
                    items(viewModel.productsState.productsList) {
                        ProductItem(item = it)
                    }
                }

                Spacer(modifier = Modifier.height(paddingJump))

                CustomButton(value = "Add product", onClick = {
                    navController.navigate(Screens.ADD_PRODUCT.route)
                })
            }
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