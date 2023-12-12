package com.example.patagonicapp.ui.dialogs

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Location
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddClientDialog(viewModel: DataViewModel, dismiss: () -> Unit) {

    var newClientName by remember { mutableStateOf("") }
    var newClientBusiness by remember { mutableStateOf("") }
    var newClientLocationId by remember { mutableStateOf<Long?>(null) }
    var showLocations by remember { mutableStateOf(false) }

    var customLocation by remember { mutableStateOf(false) }
    var newLocationName by remember { mutableStateOf("") }

    Surface(
        shape = shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "New Client",
                style = MaterialTheme.typography.h6,
                color = colors.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newClientName,
                onValueChange = { newClientName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("Name") }

            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newClientBusiness,
                onValueChange = { newClientBusiness = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("Business") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!customLocation) {
                Row {
                    OutlinedButton(
                        shape = shapes.large,
                        border = BorderStroke(
                            1.dp,
                            colors.onSurface.copy(alpha = ContentAlpha.disabled)
                        ),
                        onClick = {
                            showLocations = true
                        }
                    ) {
                        key(newClientLocationId) {
                            val selectedLocation = viewModel.getLocationById(newClientLocationId)
                            Text(
                                "Location: ${selectedLocation?.locationName ?: "null"}"
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showLocations,
                        onDismissRequest = { showLocations = false },
                    ) {
                        viewModel.locationsState.locationsList.forEach { location ->
                            DropdownMenuItem(onClick = {
                                newClientLocationId = location.locationId
                                showLocations = false
                            }) {
                                Text(location.locationName)
                            }
                        }
                        DropdownMenuItem(
                            onClick = {
                                customLocation = true
                                showLocations = false
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
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newLocationName,
                        onValueChange = {
                            newLocationName = if (it.isNotBlank() && it[0] == ' ') {
                                it.trimStart()
                            } else {
                                it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),

                        label = { Text("Location") },
                        modifier = Modifier.width(200.dp)
                    )
                    Row(Modifier.fillMaxWidth()) {

                        IconButton(onClick = {
                            viewModel.addLocation(locationName = newLocationName)
                            newLocationName = ""
                            customLocation = false
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    shape = shapes.large,
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
                                dismiss()
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