package com.example.patagonicapp.ui.customComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.ui.theme.paddingJump

@Composable
fun CustomFAB(
    icon: ImageVector = Icons.Default.Add,
    items: List<FabItem> = emptyList()
) {

    var isActive by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(isActive) {
            Column(horizontalAlignment = Alignment.End) {
                items.forEach() {

                    CustomFABItem(
                        item = it,
                        dismiss = { isActive = !isActive })

                    Spacer(modifier = Modifier.height(paddingDefault * 0.5f))

                }
            }
        }

        FloatingActionButton(onClick = { isActive = !isActive }) {
            Icon(
                imageVector = icon, contentDescription = null, modifier = if (isActive) {
                    Modifier
                        .rotate(45f)
                } else {
                    Modifier
                }
            )
        }
    }
}


@Composable
fun CustomFABItem(item: FabItem, dismiss: () -> Unit) {
    Surface(

        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        modifier = Modifier.clickable {
            item.onItemClicked()
            dismiss()
        }
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.secondary)
                .padding(vertical = paddingDefault / 2f, horizontal = paddingDefault),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = item.icon, contentDescription = null)
            Spacer(Modifier.width(paddingDefault))
            Text(text = item.label, style = MaterialTheme.typography.button)
        }
    }
}


class FabItem(
    val icon: ImageVector,
    val label: String,
    val onItemClicked: () -> Unit
)