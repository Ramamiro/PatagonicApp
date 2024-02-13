package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun RadioButtonDevolution(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
    )
    {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = label)
    }
}

@Composable
fun CustomRadioButton(
    listItems: List<String>,
    onItemClick: (String) -> Unit,
    selectedItem: String
) {
    Column() {
        listItems.forEach() {
            RadioButtonDevolution(
                label = it,
                isSelected = it == selectedItem,
                onClick = { onItemClick(it) })
        }
    }
}