package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.matchParentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import com.example.patagonicapp.ui.theme.paddingJump

@Composable
fun <T : Any> CustomSpinner(
    options: List<T>,
    value: String,
    onSelection: (T) -> Unit,
    onDisplay: (T) -> String,
    icon: ImageVector? = Icons.Default.ArrowDropDown,
    placeholder: String = "",
    textStyle: TextStyle = MaterialTheme.typography.body1,
    color: Color = MaterialTheme.colors.primary
) {
    var areOptionsVisible by remember {
        mutableStateOf(false)
    }
    Box() {
        BasicTextField(
            value = value,
            onValueChange = {},
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.White)
                        .padding(vertical = 8.dp, horizontal = 32.dp)
                ) {
                    if (icon != null) {
                        Icon(icon, contentDescription = null, tint = color)
                    }
                    Spacer(Modifier.width(paddingJump))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = textStyle.fontSize,
                                color = color
                            )
                        }
                        innerTextField()
                    }
                }
            })
        Spacer(modifier = Modifier
            .matchParentSize()
            .background(Color.Transparent)
            .clickable(
                onClick = { areOptionsVisible = true }
            ))
        DropdownMenu(
            expanded = areOptionsVisible,
            onDismissRequest = { areOptionsVisible = false },
        ) {
            options.forEach() {
                DropdownMenuItem(onClick = {
                    onSelection(it)
                    areOptionsVisible = false
                }) {
                    Text(onDisplay(it))
                }
            }
        }
    }
}