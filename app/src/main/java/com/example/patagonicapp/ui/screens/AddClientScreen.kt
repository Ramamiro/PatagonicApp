package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Location
import com.example.patagonicapp.ui.customComponents.CustomSpinner
import com.example.patagonicapp.ui.customComponents.CustomTextField
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddClientScreen(viewModel: DataViewModel, goBack: () -> Unit) {

    var newClientName by remember { mutableStateOf("") }
    var newClientBusiness by remember { mutableStateOf("") }

    var newClientLocationId by remember { mutableStateOf<Long?>(null) }
    var showLocations by remember { mutableStateOf(false) }

    var customLocation by remember { mutableStateOf(false) }
    var newLocationName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomTextField(
                value = newClientName,
                onValueChange = { newClientName = it },
                placeholder = "Client name",
                icon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(paddingDivision))

            CustomTextField(
                value = newClientBusiness,
                onValueChange = { newClientBusiness = it },
                placeholder = "Business name",
                icon = Icons.Default.Factory
            )

            Spacer(modifier = Modifier.height(paddingDivision))

            CustomTextField(
                value = newClientName,
                onValueChange = { newClientName = it },
                placeholder = "Phone number",
                icon = Icons.Default.Phone
            )

            Spacer(modifier = Modifier.height(paddingDivision))

            CustomSpinner<Location>(
                options = viewModel.locationsState.locationsList,
                value = viewModel.getLocationById(newClientLocationId)?.locationName ?: "",
                onSelection = {newClientLocationId = it.locationId},
                onDisplay = {it.locationName},
                placeholder = "Location"
                )

            Spacer(modifier = Modifier.height(paddingJump))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        if (newClientLocationId != null && newClientName != "" && newClientBusiness != "") {
                            try {
                                viewModel.addClient(
                                    Client(
                                        clientName = newClientName,
                                        clientBusiness = newClientBusiness,
                                        locationId = newClientLocationId!!
                                    )
                                )
                                goBack()
                            } catch (_: Exception) {
                            }
                        }
                    },
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun LocationSelectionButton(
    viewModel: DataViewModel,
    locationId: Long?,
    onSelectLocation: (selectedProductId: Long?) -> Unit,
) {

    var isLocationsOptionsVisible by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Box {
            OutlinedTextField(
                value = viewModel.getLocationById(locationId)?.locationName ?: "",
                onValueChange = {},
                label = { Text("Location") },
                readOnly = true,
//                    modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) }
            )
            Spacer(modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(top = 6.dp)
                .clickable(
                    onClick = { isLocationsOptionsVisible = true }
                ))
        }

        DropdownMenu(
            expanded = isLocationsOptionsVisible,
            onDismissRequest = { isLocationsOptionsVisible = false },
        ) {
            viewModel.locationsState.locationsList.forEach() {
                DropdownMenuItem(onClick = {
                    onSelectLocation(it.locationId)
                    isLocationsOptionsVisible = false
                }) {
                    Text(it.locationName)
                }
            }
            DropdownMenuItem(
                onClick = {
                    //##TODO: ADD NEW LOCATION
                },
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    }
}
