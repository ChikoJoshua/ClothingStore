package com.example.clothingstore.model

import java.io.Serializable

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int,
    val description: String = "Sin descripción disponible",
    val category: String = "General"
) : Serializable