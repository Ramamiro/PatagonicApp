package com.example.patagonicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.MoveToInbox
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.patagonicapp.room.AppDatabase
import com.example.patagonicapp.ui.screens.*
import com.example.patagonicapp.ui.theme.RoomPracticeTheme
import com.example.roompractice.viewmodels.DataViewModel

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

data class Screen(
    val route: String,
    val screen: (viewModel: ViewModel, navController: NavController) -> Unit
)

enum class Screens(val route: String) {
    TRIP(route = "Trip") {
        @Composable
        override fun Launch(viewModel: DataViewModel, navController: NavController) {
            TripScreen(viewModel = viewModel, navController = navController)
        }
    },
    SETTINGS(route = "Settings") {
        @Composable
        override fun Launch(viewModel: DataViewModel, navController: NavController) {
            SettingsScreen(viewModel = viewModel, navController = navController)
        }
    },
    CLIENTS(route = "Clients") {
        @Composable
        override fun Launch(viewModel: DataViewModel, navController: NavController) {
            ClientsScreen(viewModel = viewModel, navController = navController)
        }
    },
    ;

    @Composable
    abstract fun Launch(viewModel: DataViewModel, navController: NavController)
}


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
                    mutableStateOf(0)
                }
                var showDialog by remember { mutableStateOf(false) }

                val navigationItemList = listOf(
                    NavigationItem(
                        title = Screens.TRIP.route,
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unSelectedIcon = Icons.Outlined.ShoppingCart
                    ),
                    NavigationItem(
                        title = Screens.SETTINGS.route,
                        selectedIcon = Icons.Filled.Analytics,
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
                                            navController.navigate(item.title)
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
                                        }
                                    )
                                }
                            }
                        },
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                            ) {
                                NavHost(navController = navController, startDestination = "Trip") {

                                    composable(Screens.TRIP.route) {
                                        Screens.TRIP.Launch(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                        selectedNavigationItemIndex = 0
                                    }

                                    composable(Screens.SETTINGS.route) {
                                        Screens.SETTINGS.Launch(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
                                        selectedNavigationItemIndex = 1
                                    }

                                    composable(Screens.CLIENTS.route) {
                                        Screens.CLIENTS.Launch(
                                            viewModel = viewModel,
                                            navController = navController
                                        )
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
