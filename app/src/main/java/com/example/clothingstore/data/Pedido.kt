package com.example.clothingstore.data

data class Pedido(
    val id: String,
    val fecha: String,
    val total: Double,
    val metodoEntrega: String,
    val estado: String,
    val detalle: String
)