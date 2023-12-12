package com.example.patagonicapp.ui.customComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*

@Composable
fun CustomSpinner(
    list: List<Any>,
    preselected: Any,
    onSelection: ()->Unit
){

    var selected by remember{ mutableStateOf(preselected) }
}