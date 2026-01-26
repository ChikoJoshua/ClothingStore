package com.example.clothingstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clothingstore.data.SessionManager
import com.example.clothingstore.ui.Screen
import com.example.clothingstore.ui.catalog.CatalogScreen
import com.example.clothingstore.ui.login.LoginScreen
import com.example.clothingstore.ui.cart.CartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)

        setContent {
            ClothingStoreApp(sessionManager)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingStoreApp(sessionManager: SessionManager) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Tienda") },
                actions = {
                    IconButton(onClick = {
                        if (sessionManager.isLoggedIn()) {
                            navController.navigate(Screen.Profile.route)
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    }) {
                        Icon(Icons.Default.Person, contentDescription = "Cuenta")
                    }

                    // Botón de Carrito
                    IconButton(onClick = {
                        if (sessionManager.isLoggedIn()) {
                            navController.navigate(Screen.Cart.route)
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Catalog.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla 1: Catálogo
            composable(Screen.Catalog.route) {
                CatalogScreen(
                    onProductClick = { /* Navegar a detalle (futuro) */ }
                )
            }

            // Pantalla 2: Login
            composable(Screen.Login.route) {
                LoginScreen(
                    sessionManager = sessionManager,
                    onLoginSuccess = {

                        navController.navigate(Screen.Catalog.route) {

                            popUpTo(Screen.Catalog.route) { inclusive = true }
                        }
                    }
                )
            }

            // Pantalla 3: Carrito de Compras
            composable(Screen.Cart.route) {
                CartScreen(
                    onCheckoutClick = {
                        navController.navigate(Screen.Checkout.route)
                    }
                )
            }

            // Pantalla
            composable(Screen.Checkout.route) {
                Text("Retiro o Despacho")
            }
        }
    }
}