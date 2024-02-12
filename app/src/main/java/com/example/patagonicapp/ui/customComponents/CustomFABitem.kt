package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomFABitem(
    label : String = "fabitem",
    icon: ImageVector = Icons.Default.Check,
    onClick: ()->Unit
){
    Text(text = label)
}