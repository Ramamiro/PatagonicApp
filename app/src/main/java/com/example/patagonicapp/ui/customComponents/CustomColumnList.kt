package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision

@Composable
fun <T>CustomColumnList(
    items: List<T>,
    leadingText:(T)->String,
    endingText:(T)->String,
    endingIcon: @Composable() (T)-> Unit
){
    Column(
        Modifier
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = paddingDefault * 2f)
    ) {
        items.forEach() {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = leadingText(it),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = endingText(it),
                    )
                    endingIcon(it)
                }
            }
        }
    }
}