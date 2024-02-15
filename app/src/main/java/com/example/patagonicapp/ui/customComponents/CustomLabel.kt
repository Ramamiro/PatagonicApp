package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.patagonicapp.ui.theme.paddingDefault

@Composable
fun CustomLabel(
    label: String
){
    Row (
        Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingDefault)
    ){
        Text(text=label, style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold))
    }
}