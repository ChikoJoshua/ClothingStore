package com.example.clothingstore.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ClothingStorePrefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_EMAIL = "user_email"
    }

    // Guardar sesión
    fun createLoginSession(email: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    // Verificar LOGIN
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Cerrar sesión
    fun logout() {
        prefs.edit().clear().apply()
    }


    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }
}