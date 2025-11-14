package com.avtomatorgovli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.avtomatorgovli.core.designsystem.theme.RetailTheme
import com.avtomatorgovli.feature.auth.presentation.AuthRoute
import com.avtomatorgovli.feature.customers.CustomersRoute
import com.avtomatorgovli.feature.inventory.InventoryRoute
import com.avtomatorgovli.feature.pos.PosRoute
import com.avtomatorgovli.feature.products.ProductsRoute
import com.avtomatorgovli.feature.purchases.PurchasesRoute
import com.avtomatorgovli.feature.reports.ReportsRoute
import com.avtomatorgovli.feature.settings.SettingsRoute
import com.avtomatorgovli.feature.suppliers.SuppliersRoute
import com.avtomatorgovli.ui.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RetailTheme { RetailApp() } }
    }
}

@Composable
fun RetailApp(viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val destinations = remember {
        listOf(
            BottomNavDestination("dashboard", "Главная", Icons.Default.Store),
            BottomNavDestination("pos", "Касса", Icons.Default.PointOfSale),
            BottomNavDestination("products", "Товары", Icons.Default.Inventory2),
            BottomNavDestination("customers", "Покупатели", Icons.Default.Person),
            BottomNavDestination("reports", "Отчеты", Icons.Default.BarChart),
            BottomNavDestination("settings", "Настройки", Icons.Default.Settings)
        )
    }
    LaunchedEffect(state.currentUser) {
        if (state.currentUser == null) {
            navController.navigate("auth") {
                popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
            }
        } else if (currentRoute == "auth") {
            navController.navigate("dashboard") {
                popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (state.currentUser != null) {
                NavigationBar {
                    destinations.forEach { destination ->
                        NavigationBarItem(
                            selected = destination.route == currentRoute,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "auth",
            modifier = Modifier.padding(padding)
        ) {
            composable("auth") {
                AuthRoute(onSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("auth") { inclusive = true }
                    }
                })
            }
            composable("dashboard") {
                DashboardScreen(
                    onOpenPos = { navController.navigate("pos") },
                    onOpenInventory = { navController.navigate("inventory") },
                    onOpenPurchases = { navController.navigate("purchases") },
                    onOpenSuppliers = { navController.navigate("suppliers") }
                )
            }
            composable("pos") { PosRoute() }
            composable("products") { ProductsRoute() }
            composable("inventory") { InventoryRoute() }
            composable("purchases") { PurchasesRoute() }
            composable("customers") { CustomersRoute() }
            composable("suppliers") { SuppliersRoute() }
            composable("reports") { ReportsRoute() }
            composable("settings") { SettingsRoute() }
        }
    }
}

data class BottomNavDestination(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
