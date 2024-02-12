package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.example.patagonicapp.Screens

@Composable
fun CustomFAB(
    isExpanded: Boolean,
    
){
    Column{
        FloatingActionButton(
            onClick = {
            },
            backgroundColor = MaterialTheme.colors.secondary
        )
        {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
        }
    }
}