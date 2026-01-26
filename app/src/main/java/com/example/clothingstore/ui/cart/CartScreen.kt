package com.example.clothingstore.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clothingstore.data.DataRepository
import com.example.clothingstore.model.CartItem

@Composable
fun CartScreen(
    onCheckoutClick: () -> Unit
) {

    val cartItems = remember { mutableStateListOf<CartItem>().apply { addAll(DataRepository.cartItems.map { it.copy() }) } }

    val totalAmount = DataRepository.getCartTotal()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Tu Carrito",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío :(", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { item ->
                    CartItemRow(
                        item = item,
                        onAdd = {
                            DataRepository.addToCart(item.product)
                            cartItems.clear()
                            cartItems.addAll(DataRepository.cartItems.map { it.copy() })
                        },
                        onRemove = {
                            DataRepository.removeOneFromCart(item.product)
                            cartItems.clear()
                            cartItems.addAll(DataRepository.cartItems.map { it.copy() })
                        }
                    )
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total:", style = MaterialTheme.typography.titleLarge)
            Text(
                "$ ${totalAmount.toInt()}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCheckoutClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = cartItems.isNotEmpty()
        ) {
            Text("REALIZAR COMPRA")
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onAdd: () -> Unit, onRemove: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                color = Color.LightGray,
                shape = MaterialTheme.shapes.small
            ) {}

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.product.name, fontWeight = FontWeight.Bold)
                Text("$ ${item.product.price.toInt()}", color = Color.Gray, fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRemove) {
                    if (item.quantity == 1) {
                        Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                    } else {
                        Text("-", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text(
                    text = item.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = onAdd) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    }
}