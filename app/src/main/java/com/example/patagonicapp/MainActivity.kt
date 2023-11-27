package com.example.patagonicapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
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
import androidx.compose.ui.window.Dialog
import com.example.patagonicapp.room.AppDatabase
import com.example.patagonicapp.ui.dialogs.AddClientDialog
import com.example.patagonicapp.ui.dialogs.AddOrderDialog
import com.example.patagonicapp.ui.dialogs.AddProductDialog
import com.example.patagonicapp.ui.screens.ClientsScreen
import com.example.patagonicapp.ui.screens.ProductsScreen
import com.example.patagonicapp.ui.screens.TripScreen
import com.example.patagonicapp.ui.theme.RoomPracticeTheme
import com.example.roompractice.viewmodels.DataViewModel

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
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
                        selectedIcon = Icons.Filled.Person,
                        unSelectedIcon = Icons.Outlined.Person
                    ),
                    NavigationItem(
                        title = "Trip",
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unSelectedIcon = Icons.Outlined.ShoppingCart
                    ),
                    NavigationItem(
                        title = "Products",
                        selectedIcon = Icons.Filled.MoveToInbox,
                        unSelectedIcon = Icons.Outlined.MoveToInbox
                    )
                )

                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        Modifier.background(Color.Transparent),
                        topBar = {
                            TopAppBar(backgroundColor = MaterialTheme.colors.background) {
                            }

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
                                                    imageVector = item.selectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        })
                                }
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                showDialog = true

                            }, content = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add"
                                )
                            })
                        }
                    ) {
                        NavHost(navController = navController, startDestination = "Trip") {
                            composable("Trip") { TripScreen(viewModel = viewModel) }
                            composable("Clients") { ClientsScreen(viewModel = viewModel) }
                            composable("Products") { ProductsScreen(viewModel = viewModel) }

                        }

                        if (showDialog) {
                            Dialog(
                                onDismissRequest = { showDialog = false },
                            ) {
                                when (selectedNavigationItemIndex) {
                                    0 -> AddClientDialog(viewModel = viewModel) {
                                        showDialog = false
                                    }
                                    1-> AddOrderDialog(viewModel = viewModel) {
                                        showDialog = false
                                    }
                                    2 -> AddProductDialog(viewModel = viewModel) {
                                        showDialog = false
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}