package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ui.theme.paddingJump

@Composable
fun CustomButton(
    value: String,
    onClick: () -> Unit,
    icon: ImageVector? = null
) {
    Box(
        Modifier.clickable(onClick = onClick)
    ) {
        Row(
            Modifier
                .background(Color.White)
                .padding(vertical = 8.dp, horizontal = 32.dp)
                .fillMaxWidth()
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null)
            }
            Spacer(Modifier.width(paddingJump))
            Text(
                text = value,
            )
        }
    }
}