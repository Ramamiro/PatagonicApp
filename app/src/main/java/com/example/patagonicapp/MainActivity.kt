package com.example.patagonicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Location
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.room.AppDatabase
import com.example.patagonicapp.ui.screens.*
import com.example.patagonicapp.ui.theme.*
import com.example.patagonicapp.viewmodels.PickerViewModel
import com.example.roompractice.viewmodels.DataViewModel
import kotlin.reflect.KClass

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

enum class ClientStatus(val color: Color, val text: String) {
    INACTIVE(Color.White, "Inactivo"),
    PENDING(pendingColor, "Pendiente"),
    DELIVERED(deliveredColor, "Entregado"),
    CONCLUDED(concludedColor, "Concluido"),
    CANCELED(canceledColor, "Cancelado")
}

enum class PaymentType(val text: String) {
    CASH("Efectivo"),
    TRANSFER("Transferencia"),
    CHECK("Cheque")
}

enum class TYPE(val classType: KClass<*>) {
    CLIENT(Client::class),
    PRODUCT(Product::class),
    ORDER(Order::class),
    LOCATION(Location::class)
}

enum class Screens(val route: String) {
    MAIN(route = "Main"),
    SETTINGS(route = "Settings"),
    CLIENTS(route = "Clients"),
    ADD_CLIENT(route = "Add Client"),
    PRODUCTS(route = "Products"),
    ADD_PRODUCT(route = "Add Products"),
    ADD_ORDER(route = "Add Order"),
    ADD_LOCATION(route = "Add Location"),
    PICKER(route = "Picker"),
    SHOW_CLIENT(route = "Show Client"),
    ADD_TRIP(route="Add Trip")
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()

        val viewModel = DataViewModel(
            database.clientsDao(),
            database.productsDao(),
            database.ordersDao(),
            database.locationsDao(),
            database.tripsDao(),
            database.paymentsDao(),
            database.debtsDao(),
        )

        val pickerViewModel = PickerViewModel(viewModel)

        setContent {
            RoomPracticeTheme {
                val navController = rememberNavController()

                var selectedNavigationItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                val navigationItemList = listOf(
                    NavigationItem(
                        title = Screens.MAIN.route,
                        selectedIcon = Icons.Filled.LocalShipping,
                        unSelectedIcon = Icons.Outlined.LocalShipping
                    ),
                    NavigationItem(
                        title = Screens.SETTINGS.route,
                        selectedIcon = Icons.Filled.QueryStats,
                        unSelectedIcon = Icons.Outlined.Analytics
                    )
                )


                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier
                            .background(Color.LightGray),
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = Color.White
                            ) {
                                navigationItemList.forEachIndexed { index, item ->
                                    BottomNavigationItem(
                                        selected = selectedNavigationItemIndex == index,
                                        onClick = {
                                            navController.navigate(item.title){
                                                popUpTo(item.title) {
                                                    inclusive = true
                                                }
                                                pickerViewModel.clear()
                                            }
                                        },
                                        icon = {
                                            BadgedBox(badge = {}) {
                                                if (selectedNavigationItemIndex == index) {
                                                    Icon(
                                                        imageVector = item.selectedIcon,
                                                        contentDescription = item.title,
                                                        tint = colors.secondary
                                                    )
                                                } else {
                                                    Icon(
                                                        imageVector = item.selectedIcon,
                                                        contentDescription = item.title,
                                                        tint = colors.primary
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        content = { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                NavHost(navController = navController, startDestination = Screens.MAIN.route) {

                                    composable(Screens.MAIN.route) {
                                        selectedNavigationItemIndex = 0
                                        MainScreen(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                    }

                                    composable(Screens.SETTINGS.route) {
                                        selectedNavigationItemIndex = 1
                                        SettingsScreen(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                    }

                                    composable(Screens.CLIENTS.route) {
                                        ClientsScreen(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                    }
                                    composable(Screens.ADD_CLIENT.route) {
                                        AddClientScreen(
                                            viewModel = viewModel,
                                            navController = navController,
                                            pickerViewModel = pickerViewModel
                                        )
                                    }
                                    composable(Screens.PRODUCTS.route) {
                                        ProductsScreen(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                    }
                                    composable(Screens.ADD_PRODUCT.route) {
                                        AddProductScreen(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                    }
                                    composable(Screens.ADD_ORDER.route) {
                                        AddOrderScreen(
                                            viewModel = viewModel,
                                            navController = navController,
                                            pickerViewModel = pickerViewModel
                                        )
                                    }
                                    composable(Screens.ADD_LOCATION.route) {
                                        AddLocationScreen(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                    }
                                    composable("${Screens.PICKER.route}/{type}") {
                                        val type = it.arguments?.getString("type").toString()

                                        PickerScreen(
                                            viewModel = viewModel,
                                            navController = navController,
                                            pickerViewModel = pickerViewModel,
                                            type = type
                                        )
                                    }
                                    composable("${Screens.SHOW_CLIENT.route}/{clientId}") {
                                        val clientId= it.arguments?.getString("clientId")!!.toLong()
                                        ShowClientScreen(
                                            viewModel = viewModel,
                                            navController = navController,
                                            pickerViewModel = pickerViewModel,
                                            clientId = clientId
                                        )
                                    }
                                    composable(Screens.ADD_TRIP.route){
                                        AddTripScreen(viewModel = viewModel, navController = navController)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
