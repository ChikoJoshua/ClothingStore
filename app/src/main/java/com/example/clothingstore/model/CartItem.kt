package com.example.clothingstore.model

data class CartItem(
    val product: Product,
    var quantity: Int
) {
    // Calculo del total
    fun getSubtotal(): Double {
        return product.price * quantity
    }
}