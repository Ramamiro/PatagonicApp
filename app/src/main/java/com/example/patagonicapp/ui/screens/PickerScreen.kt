package com.example.patagonicapp.ui.screens

import android.icu.number.NumberFormatter.DecimalSeparatorDisplay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.patagonicapp.Screens
import com.example.patagonicapp.TYPE
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.viewmodels.PickerViewModel
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun PickerScreen(
    viewModel: DataViewModel,
    navController: NavController,
    pickerViewModel: PickerViewModel,
    type: String
) {
    val classType = TYPE.valueOf(type).classType

    Scaffold(
        topBar = { CustomTopBar(title = classType.simpleName!!) }
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
                when (type) {
                    TYPE.CLIENT.name -> PickerList(
                        options = viewModel.clientsState.clientsList,
                        item = {
                            CustomButton(
                                value = it.clientName,
                                onClick = {
                                    pickerViewModel.selectClient(it.clientId)
                                    navController.popBackStack()
                                })
                            Spacer(modifier = Modifier.height(paddingDivision))
                        }
                    )
                    TYPE.PRODUCT.name -> PickerList(
                        options = viewModel.productsState.productsList,
                        item = {
                            CustomButton(
                                value = it.productName,
                                onClick = {
                                    pickerViewModel.selectProduct(it.productId)
                                    navController.popBackStack()
                                })
                            Spacer(modifier = Modifier.height(paddingDivision))
                        }
                    )
                    TYPE.LOCATION.name -> PickerList(
                        options = viewModel.locationsState.locationsList,
                        item = {
                            CustomButton(
                                value = it.locationName,
                                onClick = {
                                    pickerViewModel.selectLocation(it.locationId)
                                    navController.popBackStack()
                                })
                            Spacer(modifier = Modifier.height(paddingDivision))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun <T : Any> PickerList(
    options: List<T>,
    item: @Composable (T) -> Unit
) {
    LazyColumn() {
        items(options) {
            item(it)
        }
    }
}

