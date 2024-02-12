package com.example.patagonicapp.ui.customComponents

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patagonicapp.ui.theme.paddingDefault
import java.time.format.TextStyle

@Composable
fun CustomTopBar(
    title: String = "",
    letterSpacing: TextUnit = 2.sp,
    textStyle: androidx.compose.ui.text.TextStyle = typography.h5,
    frontIcon: ImageVector? = null,
    supportButton: @Composable () -> Unit = {}
) {
    val textSizeToDp = with(LocalDensity.current) {
        textStyle.fontSize.toDp()
    }

    TopAppBar(
        backgroundColor = Color.White,
        modifier = Modifier.height(textSizeToDp + paddingDefault * 3),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = paddingDefault)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.width(paddingDefault))
                Text(
                    text = title,
                    style = textStyle,
                    color = colors.secondary,
                    fontWeight = FontWeight(700),
                    letterSpacing = letterSpacing,
                    textAlign = TextAlign.Center
                )
                if (frontIcon != null) {

                    Spacer(modifier = Modifier.width(with(LocalDensity.current) {
                        letterSpacing.toDp()
                    }))

                    Icon(
                        imageVector = frontIcon,
                        contentDescription = null,
                        tint = colors.secondary,
                        modifier = Modifier.size(
                            textSizeToDp
                        )
                    )
                }
            }
            Row(
                Modifier.align(Alignment.CenterEnd)
            ) {
                supportButton()
            }
        }
    }
}