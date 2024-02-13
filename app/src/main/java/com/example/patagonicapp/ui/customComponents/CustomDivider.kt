package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.patagonicapp.ui.theme.paddingDefault

@Composable
fun CustomDivider(
    width: Dp = paddingDefault
) {

    Spacer(modifier = Modifier.height(width * 0.5f))

    Divider(Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(width * 0.5f))

}