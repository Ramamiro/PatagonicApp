package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ui.theme.paddingJump

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    icon: ImageVector? = null,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    color: Color = MaterialTheme.colors.primary
) {
    BasicTextField(value = value, onValueChange = onValueChange, textStyle = textStyle, decorationBox = { innerTextField ->
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
                    Text(text = placeholder , color = color)
                }
                innerTextField()
            }
        }
    })
}
