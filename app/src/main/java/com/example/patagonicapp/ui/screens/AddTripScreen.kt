package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patagonicapp.Screens
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTextField
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddTripScreen(viewModel: DataViewModel, navController: NavController) {

    var tripName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(Screens.ADD_TRIP.route)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                LazyColumn() {
                    items(viewModel.tripsState.list) {
                        CustomButton(value = it.name, onClick = { /*TODO*/ })
                    }
                }

                Spacer(modifier = Modifier.height(paddingJump))

                CustomTextField(
                    value = tripName,
                    placeholder = "Trip date",
                    onValueChange = { tripName = it },
                    isNumeric = true
                )

                Spacer(modifier = Modifier.height(paddingJump))

                CustomButton(
                    value = "Add trip",
                    horizontalArrangement = Arrangement.Center,
                    onClick = {
                        try {
                            if (tripName != "") {
                                viewModel.addTrip(tripName = tripName)
                                tripName = ""
                            }
                        } catch (_: Exception) {
                        }
                    })
            }
        }
    }

}