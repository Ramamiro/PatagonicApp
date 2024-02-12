package com.example.roompractice.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patagonicapp.ClientStatus
import com.example.patagonicapp.models.*
import com.example.patagonicapp.room.*
import com.example.patagonicapp.states.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DataViewModel(
    private val clientDao: ClientsDatabaseDao,
    private val productDao: ProductsDatabaseDao,
    private val orderDao: OrdersDatabaseDao,
    private val locationsDao: LocationsDatabaseDao,
    private val tripDao: TripsDatabaseDao,
    private val paymentDao: PaymentsDatabaseDao,
    private val debtDao: DebtsDatabaseDao
) : ViewModel() {

    var clientsState by mutableStateOf(ClientsStates())
        private set
    var productsState by mutableStateOf(ProductsStates())
        private set
    var ordersState by mutableStateOf(OrdersStates())
        private set
    var locationsState by mutableStateOf(LocationsStates())
        private set
    var paymentsState by mutableStateOf(PaymentsStates())
        private set
    var tripsState by mutableStateOf(TripsStates())
        private set
    var debtsState by mutableStateOf(DebtsStates())
        private set

    var currentSortOption by mutableStateOf("Name")

    init {
        viewModelScope.launch {
            clientDao.getAllClients().collectLatest {
                clientsState = clientsState.copy(
                    clientsList = it
                )
            }
        }
        viewModelScope.launch {
            productDao.getAllProducts().collectLatest {
                productsState = productsState.copy(
                    productsList = it
                )
            }
        }
        viewModelScope.launch {

            orderDao.getAllOrders().collectLatest {
                ordersState = ordersState.copy(
                    ordersList = it
                )
                Log.d("Step 2", "Orders changed")
            }
        }
        viewModelScope.launch {
            locationsDao.getAllLocations().collectLatest {
                locationsState = locationsState.copy(
                    locationsList = it
                )
            }
        }
        viewModelScope.launch {
            paymentDao.getAllPayments().collectLatest {
                paymentsState = paymentsState.copy(
                    list = it
                )
            }
        }
        viewModelScope.launch {
            debtDao.getAllDebts().collectLatest {
                debtsState = debtsState.copy(
                    list = it
                )
            }
        }
        viewModelScope.launch {
            tripDao.getAllTrips().collectLatest {
                tripsState = tripsState.copy(
                    list = it
                )
            }
        }
    }

    // ADDS

    fun addTrip(trip: Trip) = viewModelScope.launch {
        tripDao.addTrip(trip)
    }

    fun addPayment(payment: Payment) = viewModelScope.launch {
        paymentDao.addPayment(payment)
    }

    fun addDebt(debt:Debt) = viewModelScope.launch {
        debtDao.addDebt(debt)
    }

    fun addClient(client: Client) = viewModelScope.launch {
        clientDao.addClient(client)
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        productDao.addProduct(product)
    }

    fun addOrder(order: Order) = viewModelScope.launch {

        val client = getClientById(order.clientId)!!
        clientDao.updateClient(client.copy(clientStatus = ClientStatus.PENDING))
        orderDao.addOrder(order)
        Log.d("Step 1","Order added")
    }

    fun addLocation(locationName: String) = viewModelScope.launch {
        locationsDao.addLocation(Location(locationName = formatText(locationName)))
    }

    /// GETS
//    fun getClientOrdersById(requestedClientId: Long): List<Order> {
//        return ordersState.ordersList.filter { it.clientId == requestedClientId }
//    }

    fun getProductById(requestedProductId: Long?): Product? {
        return if (requestedProductId == null) {
            null
        } else {
            productsState.productsList.find { it.productId == requestedProductId }
        }
    }

    fun getClientById(requestedClientId: Long?): Client? {
        return if (requestedClientId == null) {
            null
        } else {
            clientsState.clientsList.find { it.clientId == requestedClientId }
        }
    }

    fun getLocationById(requestedLocationId: Long?): Location? {
        return if (requestedLocationId == null) {
            null
        } else {
            locationsState.locationsList.find { it.locationId == requestedLocationId }
        }
    }

    fun deleteLocationById(locationId: Long) = viewModelScope.launch {
        locationsDao.deleteLocation(locationId = locationId)
    }

    fun updateClient(client: Client) = viewModelScope.launch {
        clientDao.updateClient(client)
    }

    fun updateOrder(order: Order) = viewModelScope.launch{
        orderDao.updateOrder(order)
    }

    private fun formatText(text: String): String {
        val words = text.trim().split("\\s+".toRegex())
        return words.joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
    }

}