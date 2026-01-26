package com.example.clothingstore.data

import com.example.clothingstore.model.CartItem
import com.example.clothingstore.model.Order
import com.example.clothingstore.model.Product
import com.example.clothingstore.R

object DataRepository {

    //CATÁLOGO
    val productsList = listOf(
        Product(1, "Camisa Negra", 10990, R.drawable.camisa2),
        Product(2, "Camisa Blanca", 12990, R.drawable.camisa1),
        Product(3, "Polera Verde", 7500, R.drawable.polera1),
        Product(4, "Camiseta Rosada", 7500, R.drawable.polera2),
        Product(5, "Camiseta Celeste", 15990, R.drawable.polera3),
        Product(6, "Camiseta blanca mangas negras", 15990, R.drawable.polera4),
        Product(7, "Pantalón Azul", 15990, R.drawable.pantalon1),
        Product(8, "Pantalón Cargo", 15990, R.drawable.pantaloncargo1),
        Product(9, "Chaqueta", 15990, R.drawable.chaquetaa),
        Product(10, "Zapatillas", 15990, R.drawable.zapatillas1),
        Product(11, "Zapatillas Deportivas", 15990, R.drawable.zapatillas3),

    )

    //CARRITO DE COMPRAS
    val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
    }

    fun removeOneFromCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity--
            } else {
                cartItems.remove(existingItem)
            }
        }
    }

    fun getCartTotal(): Int {
        return cartItems.sumOf { it.getSubtotal() }
    }

    fun clearCart() {
        cartItems.clear()
    }

    // HISTORIAL DE COMPRAS
    val ordersHistory = mutableListOf<Order>()

    fun addOrder(order: Order) {
        ordersHistory.add(0, order) // Agrega al principio de la lista (más reciente primero)
    }
}