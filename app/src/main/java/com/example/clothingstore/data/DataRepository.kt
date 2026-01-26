package com.example.clothingstore.data

import com.example.clothingstore.R
import com.example.clothingstore.model.CartItem
import com.example.clothingstore.model.Product

object DataRepository {

    // PÁGINA 1
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
        Product(11, "Zapatillas Deportivas", 15990, R.drawable.zapatillas3)
    )

    // PÁGINA 2
    val productsList2 = listOf(
        Product(12, "Zapatos Dama", 15990, R.drawable.zapatosdama1),
        Product(13, "Chaqueta Roja", 15990, R.drawable.chaqueta1),
        Product(14, "Chaqueta Amarilla", 15990, R.drawable.chaqueta2),
        Product(15, "Chaqueta Gris", 15990, R.drawable.chaqueta3),
        Product(16, "Chaqueta Negra", 15990, R.drawable.chaqueta4),
        Product(17, "Chaleco negro", 15990, R.drawable.chaleco1),
        Product(18, "Chaleco amarillo", 15990, R.drawable.chaleco2),
        Product(19, "Chaleco verde", 15990, R.drawable.chaleco3),
        Product(20, "Chaleco blanco", 15990, R.drawable.chaleco4)
    )

    // Gestión del Carrito
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

    fun getCartTotal(): Double {
        return cartItems.sumOf { it.product.price.toDouble() * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}