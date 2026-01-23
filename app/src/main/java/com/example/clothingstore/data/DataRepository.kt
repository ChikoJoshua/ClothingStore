package com.example.clothingstore.data

import com.example.clothingstore.model.CartItem
import com.example.clothingstore.model.Order
import com.example.clothingstore.model.Product

object DataRepository {

    // --- CATÁLOGO DE PRODUCTOS (Simulado) ---
    val productsList = listOf(
        Product(1, "Polera Básica Blanca", "100% Algodón, ajuste perfecto.", 9990.0, "", "Poleras"),
        Product(2, "Jeans Slim Fit", "Denim azul oscuro elásticado.", 24990.0, "", "Pantalones"),
        Product(3, "Zapatillas Urbanas", "Estilo casual para el día a día.", 39990.0, "", "Calzado"),
        Product(4, "Chaqueta de Cuero", "Cuero sintético de alta calidad.", 45990.0, "", "Chaquetas"),
        Product(5, "Gorro de Lana", "Ideal para el invierno.", 5990.0, "", "Accesorios"),
        Product(6, "Camisa a Cuadros", "Estilo leñador, muy cómoda.", 18990.0, "", "Camisas")
    )

    // --- CARRITO DE COMPRAS ---
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
        return cartItems.sumOf { it.getSubtotal() }
    }

    fun clearCart() {
        cartItems.clear()
    }

    // --- HISTORIAL DE ORDENES ---
    val ordersHistory = mutableListOf<Order>()

    fun addOrder(order: Order) {
        ordersHistory.add(0, order) // Agrega al principio de la lista (más reciente primero)
    }
}