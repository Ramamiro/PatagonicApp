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
import java.text.NumberFormat
import java.util.*

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

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

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

    fun addTrip(tripName: String) = viewModelScope.launch {
        endActiveTrip()
        tripDao.addTrip(Trip(name = tripName))
    }

    fun addPayment(payment: Payment) = viewModelScope.launch {
        paymentDao.addPayment(payment)
    }

    fun addDebt(debt: Debt) = viewModelScope.launch {
        debtDao.addDebt(debt)
    }

    fun addClient(client: Client) = viewModelScope.launch {
        val formattedClient = client.copy(
            clientName = formatText(client.clientName),
            clientBusiness = formatText(client.clientName)
        )
        clientDao.addClient(formattedClient)
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        productDao.addProduct(product)
    }

    fun addOrder(order: Order, clientId: Long) = viewModelScope.launch {
        val client = getClientById(clientId)
        clientDao.updateClient(client!!.copy(clientStatus = ClientStatus.PENDING))
        val previousOrder =
            getOrdersByClientId(
                clientId = clientId,
                tripId = getActiveTrip()?.id
            ).find { it.productId == order.productId }
        if (previousOrder == null) {
            orderDao.addOrder(order)
        } else {
            val product = getProductById(order.productId)!!
            updateOrder(
                order = previousOrder.copy(
                    quantity = previousOrder.quantity + order.quantity,
                    total = (previousOrder.quantity + order.quantity) * product.pricePerKg * product.kgPerUnit,
                    weight = (previousOrder.quantity + order.quantity) * product.kgPerUnit
                )
            )
        }
    }

    fun addLocation(locationName: String) = viewModelScope.launch {
        locationsDao.addLocation(Location(locationName = formatText(locationName)))
    }

    /// GETS

    fun getOrdersByClientId(clientId: Long, tripId: Long? = null): List<Order> {
        val clientOrders = ordersState.ordersList.filter { it.clientId == clientId }
        return if (tripId == null) {
            clientOrders
        } else {
            clientOrders.filter { it.tripId == tripId }
        }
    }

    fun getPaymentsByClientId(clientId: Long, tripId: Long?): List<Payment> {
        val clientPayments = paymentsState.list.filter { it.clientId == clientId }
        return if (tripId == null) {
            clientPayments
        } else {
            clientPayments.filter { it.tripId == tripId }
        }
    }

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

    fun getActiveClients(): List<Client> {
        return clientsState.clientsList.filter { it.clientStatus != ClientStatus.INACTIVE }
    }

    fun getActiveTrip(): Trip? {
        return tripsState.list.find { it.active }
    }

    fun deleteLocationById(locationId: Long) = viewModelScope.launch {
        locationsDao.deleteLocation(locationId = locationId)
    }

    fun deletePaymentById(id: Long) = viewModelScope.launch {
        paymentDao.deletePaymentById(id = id)
    }

    fun deleteOrderById(orderId: Long, clientId: Long) = viewModelScope.launch {
        val orders = getOrdersByClientId(clientId)
        if (orders.size == 1) {
            val client = getClientById(clientId)!!
            updateClient(client.copy(clientStatus = ClientStatus.INACTIVE))
        }
        orderDao.deleteOrderById(id = orderId)
    }

    fun updateClient(client: Client) = viewModelScope.launch {
        clientDao.updateClient(client)
    }

    fun updateOrder(order: Order) = viewModelScope.launch {
        orderDao.updateOrder(order)
    }

    fun endActiveTrip() = viewModelScope.launch {
        val activeTrip = getActiveTrip()

        if (activeTrip != null) {
            tripDao.updateTrip(activeTrip.copy(active = false))
            getActiveClients().forEach() {
                updateClient(it.copy(clientStatus = ClientStatus.INACTIVE))
            }

        }

    }

    private fun formatText(text: String): String {
        val words = text.trim().split("\\s+".toRegex())
        return words.joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
    }

    fun formatMoney(amount: Double):String{
        return currencyFormat.format(amount)
    }
}