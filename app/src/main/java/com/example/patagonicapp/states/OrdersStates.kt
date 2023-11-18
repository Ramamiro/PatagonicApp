package com.example.patagonicapp.states

import com.example.patagonicapp.models.Order

data class OrdersStates(
    val ordersList: List<Order> = emptyList()
)
