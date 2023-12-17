package com.example.patagonicapp.ui.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.patagonicapp.viewmodels.PickerViewModel
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddLocationScreen(
    viewModel: DataViewModel,
    navController: NavController,
    pickerViewModel: PickerViewModel
) {
    fun popBack() {
        pickerViewModel.clear()
        navController.popBackStack()
    }

    BackHandler() {
        Log.d("Proper back", "true")
        popBack()

    }

    var locationName by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CustomTopBar(Screens.ADD_LOCATION.route)
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
                LazyColumn(){
                    items(viewModel.locationsState.locationsList){
                        Text(it.locationName)
                    }
                }

                Spacer(modifier = Modifier.height(paddingJump))

                CustomTextField(value = locationName, onValueChange = { locationName = it })

                Spacer(modifier = Modifier.height(paddingJump))

                CustomButton(
                    value = "Add location",
                    onClick = {
                        try {
                            if (locationName != "") {
                                viewModel.addLocation(locationName = locationName)
                                locationName = ""
                            }
                        }catch (_:Exception){
                        }
                    })
            }
        }
    }
}