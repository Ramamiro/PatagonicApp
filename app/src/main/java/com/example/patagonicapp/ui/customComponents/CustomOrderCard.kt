package com.example.patagonicapp.ui.customComponents

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun CustomOrderCard(viewModel: DataViewModel, client: Client, orders: List<Order>) {
    val total = orders.sumOf { it.total }

    var isStatusMenuVisible by remember { mutableStateOf(false) }
    var areOrdersVisible by remember { mutableStateOf(false) }

    var tapped by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    key(client) {
        Log.d(client.clientName, client.clientStatus.name)
        Surface(
            elevation = 3.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .drawBehind {

                        backgroundBox(client.clientStatus)
                    }
                    .indication(interactionSource, LocalIndication.current)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { offset ->
                                tapped = true
                                val press = PressInteraction.Press(offset)
                                interactionSource.emit(press)
                                tryAwaitRelease()
                                interactionSource.emit(PressInteraction.Release(press))
                                tapped = false
                            },
                            onLongPress = {
                                orders.forEach() {
                                    val product = viewModel.getProductById(it.productId)
                                    Log.d(
                                        "${product?.productName} price",
                                        product?.pricePerKg.toString()
                                    )
                                    Log.d(
                                        "${product?.productName} kg",
                                        product?.kgPerUnit.toString()
                                    )

                                }
                            },
                            onTap = {
                                areOrdersVisible = !areOrdersVisible
                            }
                        )
                    }
                    .padding(
                        start = paddingDefault * 2,
                        top = paddingDefault,
                        end = paddingDefault,
                        bottom = paddingDefault
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top
            ) {
                key(client) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isStatusMenuVisible = true
                                Log.d("MenuVisible", isStatusMenuVisible.toString())
                            }
                        )
                        Spacer(modifier = Modifier.width(paddingDefault))
                        StatusMenu(
                            client = client,
                            viewModel = viewModel,
                            isStatusMenuVisible = isStatusMenuVisible,
                            dismiss = { isStatusMenuVisible = false })

                        Column() {

                            Text(
                                text = "${client.clientBusiness} ${client.clientName}",
                                style = MaterialTheme.typography.h6,
                            )

                            Text(
                                text = viewModel.getLocationById(client.locationId)?.locationName
                                    ?: "",
                                style = MaterialTheme.typography.caption
                            )

                        }
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            key(total) {
                                Text(
                                    text = "$ ${total}",
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                            Icon(
                                imageVector = if (
                                    !areOrdersVisible
                                ) {
                                    Icons.Default.ExpandMore
                                } else {
                                    Icons.Default.ExpandLess
                                },
                                contentDescription = null
                            )

                        }
                    }
                }
                if (areOrdersVisible) {

                    Row {
                        Spacer(modifier = Modifier.width(paddingDefault + 24.dp))

                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Spacer(modifier = Modifier.height(paddingDefault * 0.5f))

                            Divider(Modifier.fillMaxWidth())

                            Spacer(modifier = Modifier.height(paddingDefault * 0.5f))

                            orders.forEach() {
                                val product = viewModel.getProductById(it.productId)
                                Text(text = "${product?.productName ?: ""}: ${it.quantity}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusMenu(
    client: Client,
    viewModel: DataViewModel,
    isStatusMenuVisible: Boolean,
    dismiss: () -> Unit
) {
    DropdownMenu(
        expanded = isStatusMenuVisible,
        onDismissRequest = { dismiss() }
    ) {
        for (statusValue in ClientStatus.values()) {
            if (statusValue != ClientStatus.INACTIVE) {
                DropdownMenuItem(onClick = {
                    viewModel.updateClient(client.copy(clientStatus = statusValue))
                    dismiss()
                }) {
                    Text(text = statusValue.name)
                }
            }
        }
    }
}

fun DrawScope.backgroundBox(
    status: ClientStatus
) {
    drawRoundRect(
        color = status.color,
        size = Size(16.dp.toPx(), size.height)
    )
}

//@Composable
//fun OrderItemComponent() {
//
//    var isStatesMenuVisible by remember { mutableStateOf(false) }
//
//    Surface(
//        elevation = 3.dp,
//        shape = RoundedCornerShape(15),
//        modifier = Modifier
//            .background(color = MaterialTheme.colors.background)
//
//    ) {
//        Row(
//            modifier = Modifier
//
//                .drawBehind {
//                    backgroundBox(
//                        state = order.state
//                    )
//                }
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            Image(
//                imageVector = Icons.Default.ContentPaste,
//                contentDescription = null,
//                modifier = Modifier.clickable {
//                    isStatesMenuVisible = true
//                    Log.d("MenuVisible", isStatesMenuVisible.toString())
//                }
//            )
//            Column() {
//
//
//                Text(
//                    text = "${order.owner.business} ${order.owner.name}",
//                    style = MaterialTheme.typography.h6,
//                    modifier = Modifier
//                        .padding(start = 16.dp)
//                )
//                Text(
//                    text = order.owner.location,
//                    style = MaterialTheme.typography.caption,
//                    modifier = Modifier.padding(start = 16.dp)
//                )
//
//            }
//            Row(
//                horizontalArrangement = Arrangement.End,
//                modifier = Modifier.fillMaxWidth()
//            ) {}
//        }
//    }
//}