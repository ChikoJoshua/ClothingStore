package com.example.clothingstore.model

import java.io.Serializable

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String, // Usaremos esto para cargar fotos después
    val category: String
) : Serializable