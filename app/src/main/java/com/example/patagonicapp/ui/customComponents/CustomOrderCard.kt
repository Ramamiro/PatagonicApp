package com.example.patagonicapp.ui.customComponents

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.ui.dialogs.AddPaymentDialog
import com.example.patagonicapp.ui.screens.StatusMenu
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.patagonicapp.ui.theme.paddingJump
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun CustomOrderCard(
    viewModel: DataViewModel,
    client: Client,
    onLongPress: () -> Unit
) {
    var isStatusMenuVisible by remember { mutableStateOf(false) }
    var areOrdersVisible by remember { mutableStateOf(false) }

    var tapped by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    var isDialogVisible by remember { mutableStateOf(false) }

    val orders = viewModel.getOrdersByClientId(client.clientId, viewModel.getActiveTrip()?.id)
    val payments = viewModel.getPaymentsByClientId(client.clientId, viewModel.getActiveTrip()?.id)

    key(client) {
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
                            onLongPress = { onLongPress() },
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
                                onLongPress()
                            }
                        )
                        Spacer(modifier = Modifier.width(paddingDefault))
                        StatusMenu(
                            client = client,
                            viewModel = viewModel,
                            isStatusMenuVisible = isStatusMenuVisible,
                            dismiss = { isStatusMenuVisible = false })

                        Column() {
                            Row() {

                                Text(
                                    text = "${client.clientName} ",
                                    style = MaterialTheme.typography.h6,
                                )
//                                Text(
//                                    text = "${client.clientBusiness}",
//                                    style = MaterialTheme.typography.h6,
//                                )
                            }


                            Row() {
                                Text(
                                    text = viewModel.getLocationById(client.locationId)?.locationName
                                        ?: "",
                                    style = MaterialTheme.typography.caption
                                )
                                Text(
                                    text = " - ",
                                    style = MaterialTheme.typography.caption
                                )
                                Text(
                                    text = client.clientStatus.text,
                                    style = MaterialTheme.typography.caption,
                                    color = client.clientStatus.color
                                )
                            }

                        }
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

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

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp + paddingDefault)
                    ) {


                        Column(
                            Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start

                        ) {


                            CustomDivider()

                            Column(Modifier.padding(start = paddingDefault)) {

                                orders.forEach() {
                                    val product = viewModel.getProductById(it.productId)!!
                                    Text(text = "${product.productName}  x  ${it.quantity}")
                                }
                            }

                            CustomDivider()

                            key(orders, payments) {
                                Column(
                                    modifier = Modifier.padding(start = paddingDefault)
                                ) {

                                    Text(
                                        text = "Total   " + viewModel.formatMoney(orders.sumOf { it.total }),
                                        fontStyle = FontStyle.Italic
                                    )

                                    Text(
                                        text = "Pagado   " + viewModel.formatMoney(payments.sumOf { it.amount }),
                                        fontStyle = FontStyle.Italic
                                    )

                                }
                            }
                        }
                    }
                }
                if (isDialogVisible) {
                    Dialog(onDismissRequest = { isDialogVisible = false }) {
                        AddPaymentDialog(viewModel = viewModel, client = client) {
                            isDialogVisible = false
                        }
                    }
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