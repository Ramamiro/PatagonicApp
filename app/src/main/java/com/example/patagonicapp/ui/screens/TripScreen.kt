package com.example.patagonicapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.Screens
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.ui.customComponents.CustomFAB
import com.example.patagonicapp.ui.customComponents.CustomOrderCard
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun TripScreen(viewModel: DataViewModel, navController: NavController) {


    viewModel.clientsState.clientsList.forEach(){
        Log.d(it.clientName, it.clientStatus.name)
    }

    val sortOptions = mapOf<String, Comparator<Client>>(
        "Name" to compareBy { it.clientName },
        "Business" to compareBy { it.clientBusiness },
        "Status" to compareBy { it.clientStatus }
    )
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "PatagonicApp",
                frontIcon = Icons.Default.LocalShipping,
                supportButton = {
                    var areSortOptionsVisible by remember { mutableStateOf(false) }
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier =
                        Modifier.clickable {
                            areSortOptionsVisible = true
                        })
                    DropdownMenu(
                        expanded = areSortOptionsVisible,
                        onDismissRequest = { areSortOptionsVisible = false }) {
                        sortOptions.forEach() {

                            DropdownMenuItem(onClick = {
                                areSortOptionsVisible = true
                            }) {
                                DropdownMenuItem(onClick = {
                                    viewModel.currentSortOption = it.key
                                }) {
                                    Text(text = it.key)
                                }
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.clientsState.clientsList.forEach(){
                        Log.d(it.clientName, it.clientBusiness)
                    }
                    navController.navigate(Screens.ADD_ORDER.route)}
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingDefault)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn() {
                    items(
                        viewModel.clientsState.clientsList
                            .sortedWith(sortOptions[viewModel.currentSortOption]!!)
                    )
                    {
                        val client = viewModel.getClientById(it.clientId)
                        if (client!!.clientStatus != ClientStatus.INACTIVE) {
                            val clientOrders = viewModel.getOrdersByClientId(it.clientId)
                            CustomOrderCard(
                                viewModel = viewModel,
                                client = it,
                                orders = clientOrders
                            )
                            Spacer(modifier = Modifier.height(paddingJump))
                        }
                    }
                }
            }
        }
    }
}

