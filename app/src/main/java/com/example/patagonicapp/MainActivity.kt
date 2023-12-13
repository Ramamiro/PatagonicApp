package com.example.patagonicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.MoveToInbox
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.patagonicapp.room.AppDatabase
import com.example.patagonicapp.ui.screens.*
import com.example.patagonicapp.ui.theme.RoomPracticeTheme
import com.example.roompractice.viewmodels.DataViewModel

data class NavigationItem(
    val title: String,
    val FABRoute: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,

    )

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()

        val viewModel = DataViewModel(
            database.clientsDao(),
            database.productsDao(),
            database.ordersDao(),
            database.locationsDao()
        )

        setContent {
            RoomPracticeTheme {
                val navController = rememberNavController()

                var selectedNavigationItemIndex by rememberSaveable {
                    mutableStateOf(1)
                }
                var showDialog by remember { mutableStateOf(false) }

                val navigationItemList = listOf(
                    NavigationItem(
                        title = "Clients",
                        FABRoute = "AddClient",
                        selectedIcon = Icons.Filled.Person,
                        unSelectedIcon = Icons.Outlined.Person
                    ),
                    NavigationItem(
                        title = "Trip",
                        FABRoute = "AddOrder",
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unSelectedIcon = Icons.Outlined.ShoppingCart
                    ),
                    NavigationItem(
                        title = "Products",
                        FABRoute = "AddProduct",
                        selectedIcon = Icons.Filled.MoveToInbox,
                        unSelectedIcon = Icons.Outlined.MoveToInbox
                    )
                )

                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        backgroundColor= MaterialTheme.colors.background,
                        modifier = Modifier
                            .background(Color.LightGray),
                        topBar = {
                            TopAppBar(backgroundColor = Color.White) {}
                        },
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = Color.White
                            ) {
                                navigationItemList.forEachIndexed { index, item ->
                                    BottomNavigationItem(
                                        selected = selectedNavigationItemIndex == index,
                                        onClick = {
                                            navController.navigate(item.title)
                                            selectedNavigationItemIndex = index
                                        },
                                        icon = {
                                            BadgedBox(badge = {}) {
                                                Icon(
                                                    imageVector = if (selectedNavigationItemIndex == index) {
                                                        item.selectedIcon
                                                    } else {
                                                        item.unSelectedIcon
                                                    },
                                                    contentDescription = item.title
                                                )
                                            }
                                        })
                                }
                            }
                        },
                        floatingActionButton = {

                                FloatingActionButton(
                                    onClick = {
                                        navController.navigate(
                                            navigationItemList[selectedNavigationItemIndex].FABRoute
                                        )
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add"
                                        )
                                    }
                                )
                        }
                    ) {
                        NavHost(navController = navController, startDestination = "Trip") {

                            composable("Trip") { TripScreen(viewModel = viewModel) }
                            composable("Clients") { ClientsScreen(viewModel = viewModel) }
                            composable("Products") { ProductsScreen(viewModel = viewModel) }

                            composable("AddProduct") { AddProductScreen(viewModel = viewModel) }
                            composable("AddClient") { AddClientScreen(viewModel = viewModel, goBack = {navController.navigate(navigationItemList[selectedNavigationItemIndex].title)}) }
                            composable("AddOrder") { AddOrderScreen(viewModel = viewModel) }

                        }
                    }
                }
            }
        }
    }
}
