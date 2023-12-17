package com.example.patagonicapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.patagonicapp.Screens
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun SettingsScreen(viewModel: DataViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBar("Settings")
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = paddingDefault)
        )
        {
            Column(
                Modifier.fillMaxSize()
            ) {

                CustomButton(
                    value = "Clients",
                    icon = Icons.Default.Person,
                    onClick = {
                        navController.navigate(Screens.CLIENTS.route)
                    })

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomButton(
                    value = "Products",
                    icon = Icons.Default.Label,
                    onClick = {
                        navController.navigate(Screens.PRODUCTS.route)
                    })

                Spacer(modifier = Modifier.height(paddingDivision))

                CustomButton(
                    value = "Locations",
                    icon = Icons.Default.Map,
                    onClick = {
                        navController.navigate(Screens.ADD_LOCATION.route)
                    })

                Spacer(modifier = Modifier.height(paddingDivision))
            }
        }
    }
}