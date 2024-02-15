package com.example.patagonicapp.ui.dialogs

import android.widget.RadioGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.PaymentType
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Payment
import com.example.patagonicapp.ui.customComponents.CustomButton
import com.example.patagonicapp.ui.customComponents.CustomRadioButton
import com.example.patagonicapp.ui.customComponents.CustomTextField
import com.example.patagonicapp.ui.theme.paddingDefault
import com.example.patagonicapp.ui.theme.paddingDivision
import com.example.roompractice.viewmodels.DataViewModel

@Composable
fun AddPaymentDialog(viewModel: DataViewModel, client: Client, dismiss: () -> Unit) {

    var amount by remember { mutableStateOf("") }
    var selectedPaymentType by remember { mutableStateOf(PaymentType.CASH.text) }
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 32.dp),
            ) {
                Text(text = "Payment")
            }

            Spacer(modifier = Modifier.height(paddingDivision))

            CustomTextField(
                value = amount,
                placeholder = "Monto",
                onValueChange = { amount = it },
                isNumeric = true
            )

            Spacer(modifier = Modifier.height(paddingDivision))

            CustomRadioButton(
                listItems = PaymentType.values().map { it.text },
                onItemClick = { selectedPaymentType = it },
                selectedItem = selectedPaymentType
            )

            Spacer(modifier = Modifier.height(paddingDivision))

            CustomButton(value = "Add", onClick = {
                val activeTrip = viewModel.getActiveTrip()
                if (activeTrip != null) {
                    viewModel.addPayment(
                        Payment(
                            clientId = client.clientId,
                            type = PaymentType.values().find { it.text == selectedPaymentType }!!,
                            amount = amount.toDouble(),
                            tripId = activeTrip.id
                        )
                    )
                }
                dismiss()
            })

        }
    }
}