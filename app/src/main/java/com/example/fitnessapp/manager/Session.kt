package com.example.fitnessapp.manager

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val USER_LOGIN = "user_login"
    }

    fun createLoginSession(login: String) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(USER_LOGIN, login)
        editor.apply()
    }


    fun getUserLogin(): String? {
        return prefs.getString(USER_LOGIN, null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}