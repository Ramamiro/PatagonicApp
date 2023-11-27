package com.example.roompractice.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Location
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.room.ClientsDatabaseDao
import com.example.patagonicapp.room.LocationsDatabaseDao
import com.example.patagonicapp.room.OrdersDatabaseDao
import com.example.patagonicapp.room.ProductsDatabaseDao
import com.example.patagonicapp.states.ClientsStates
import com.example.patagonicapp.states.LocationsStates
import com.example.patagonicapp.states.OrdersStates
import com.example.patagonicapp.states.ProductsStates
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DataViewModel(
    private val clientDao: ClientsDatabaseDao,
    private val productDao: ProductsDatabaseDao,
    private val orderDao: OrdersDatabaseDao,
    private val locationsDao: LocationsDatabaseDao
) : ViewModel() {

    var clientsState by mutableStateOf(ClientsStates())
        private set
    var productsState by mutableStateOf(ProductsStates())
        private set
    var ordersState by mutableStateOf(OrdersStates())
        private set
    var locationsState by mutableStateOf(LocationsStates())
        private set

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
                Log.d("HERE", "here")
            }
        }
        viewModelScope.launch {
            orderDao.getAllOrders().collectLatest {
                ordersState = ordersState.copy(
                    ordersList = it
                )
            }
        }
        viewModelScope.launch {
            locationsDao.getAllLocations().collectLatest {
                locationsState = locationsState.copy(
                    locationsList = it
                )
            }
        }
    }


    fun addClient(client: Client) = viewModelScope.launch {
        clientDao.addClient(client)
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        productDao.addProduct(product)
    }

    fun addOrder(order: Order) = viewModelScope.launch {
        orderDao.addOrder(order)
    }

    fun addLocation(locationName: String) = viewModelScope.launch  {

        val words = locationName.trim().split("\\s+".toRegex())
        val recomposedWord =  words.joinToString(" "){ it.replaceFirstChar { char -> char.uppercaseChar() }}

        locationsDao.addLocation( Location(locationName = recomposedWord) )
    }

    fun deleteLocationById(locationId: Long) = viewModelScope.launch{

        locationsDao.deleteLocation(locationId = locationId)
    }

    fun getClientOrdersById(requestedClientId: Long): List<Order> {
        return ordersState.ordersList.filter { it.clientId == requestedClientId }
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
}