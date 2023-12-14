package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.patagonicapp.ui.theme.paddingDefault

@Composable
fun CustomTopBar(title: String = "") {
    TopAppBar(
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = paddingDefault)
        ) {
            Text(text = title)
        }
    }
}