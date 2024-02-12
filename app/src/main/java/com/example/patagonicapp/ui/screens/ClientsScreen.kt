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
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomTopBar
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun ClientsScreen(viewModel: DataViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBar("Clients")
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                LazyColumn() {
                    items(viewModel.clientsState.clientsList) {
                        ClientItem(item = it, viewModel)
                    }
                }

                Spacer(modifier = Modifier.height(paddingJump))

                CustomButton(
                    value = "Add client",
                    onClick = { navController.navigate(Screens.ADD_CLIENT.route) })
            }
        }
    }
}
@Composable
fun ClientItem(item: Client, viewModel: DataViewModel) {
    Row()
    {

        CustomButton(value = item.clientName, onClick = { /*TODO*/ })

//        Text(text = item.clientId.toString())
//        Text(text = item.clientName)
//        Text(text = item.clientBusiness)
//        Text(text = viewModel.getLocationById(item.locationId)?.locationName ?: "")

    }
}