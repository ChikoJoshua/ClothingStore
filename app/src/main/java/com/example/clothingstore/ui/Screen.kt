package com.example.clothingstore.ui

sealed class Screen(val route: String) {
    object Catalog : Screen("catalog")
    object Cart : Screen("cart")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Checkout : Screen("checkout")
}