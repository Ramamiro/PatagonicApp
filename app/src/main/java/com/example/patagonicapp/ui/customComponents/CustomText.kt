package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ui.theme.paddingDefault

@Composable
fun CustomText(

    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    color: Color = Color.Black,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = paddingDefault * 2f, vertical = 8.dp),
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            text = text,
            color = color,
            style = style
        )
    }
}