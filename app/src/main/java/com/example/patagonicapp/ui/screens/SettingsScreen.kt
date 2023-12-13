package com.example.patagonicapp.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun SettingsScreen(viewModel: DataViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White
            ) {}
        }
    )
    {

    }
}