package com.example.clothingstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.clothingstore.data.DataRepository
import com.example.clothingstore.data.SessionManager
import com.example.clothingstore.ui.Screen
import com.example.clothingstore.ui.catalog.CatalogScreen
import com.example.clothingstore.ui.login.LoginScreen
import com.example.clothingstore.ui.cart.CartScreen
import com.example.clothingstore.ui.pedido.PedidoScreen
import com.example.clothingstore.ui.historial.HistorialScreen

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
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val displayName = remember(navBackStackEntry) {
        val email = sessionManager.getUserEmail()
        if (sessionManager.isLoggedIn() && email != null) {
            email.substringBefore("@")
        } else {
            null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(if (displayName != null) 65.dp else 40.dp)
                                .padding(end = 8.dp)
                        )
                        Column {
                            Text(
                                text = "Clothing Store",
                                fontSize = if (displayName != null) 18.sp else 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (displayName != null) {
                                Text(
                                    text = "Hola, $displayName",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                },
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

                    CartIconWithBadge(onClick = {
                        if (sessionManager.isLoggedIn()) {
                            navController.navigate(Screen.Cart.route)
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    })
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
            composable(Screen.Catalog.route) {
                CatalogScreen(onProductClick = { })
            }

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

            composable(Screen.Cart.route) {
                CartScreen(
                    onCheckoutClick = {
                        navController.navigate(Screen.Checkout.route)
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    sessionManager = sessionManager,
                    onLogout = {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(0)
                        }
                    },
                    onVerHistorial = {
                        navController.navigate("historial")
                    }
                )
            }

            composable(Screen.Checkout.route) {
                PedidoScreen(
                    onVolverAlCatalogo = {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Catalog.route) { inclusive = true }
                        }
                    }
                )
            }

            composable("historial") {
                HistorialScreen(
                    onVolverAlCatalogo = {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Catalog.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onVerHistorial: () -> Unit
) {
    val email = sessionManager.getUserEmail() ?: "juan"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = Color(0xFF7E57C2).copy(alpha = 0.8f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hola, $email",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onVerHistorial,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text("Mis Pedidos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                sessionManager.logout()
                onLogout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text("Cerrar Sesión", color = Color.White)
        }
    }
}

@Composable
fun CartIconWithBadge(onClick: () -> Unit) {
    val itemCount = DataRepository.cartItems.sumOf { it.quantity }

    Box(modifier = Modifier.padding(end = 8.dp)) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Carrito",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (itemCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
                    .size(18.dp)
                    .background(Color.Red, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (itemCount > 9) "+9" else itemCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}