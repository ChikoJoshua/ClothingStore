package com.example.clothingstore.model

import java.io.Serializable
import java.util.Date

data class Order(
    val id: String,
    val date: Date,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: String, // estado del envio
    val deliveryType: String, // Retiro o Despacho
    val deliveryAddress: String? = null
) : Serializable