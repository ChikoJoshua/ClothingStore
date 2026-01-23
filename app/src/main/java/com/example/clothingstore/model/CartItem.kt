package com.example.clothingstore.model

data class CartItem(
    val product: Product,
    var quantity: Int
) {
    // Calcula el total de este ítem (Precio x Cantidad)
    fun getSubtotal(): Double {
        return product.price * quantity
    }
}