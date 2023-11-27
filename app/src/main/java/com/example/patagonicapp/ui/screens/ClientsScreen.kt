package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.patagonicapp.models.Client
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun ClientsScreen(viewModel: DataViewModel) {
    LazyColumn() {
        items(viewModel.clientsState.clientsList) {
            ClientItem(item = it, viewModel)
        }
    }
}

@Composable
fun ClientItem(item: Client, viewModel: DataViewModel) {
    Row() {

        Text(text = item.clientId.toString())
        Text(text = item.clientName)
        Text(text = item.clientBusiness)
        Text(text = viewModel.getLocationById(item.locationId)?.locationName ?: "")

    }
}