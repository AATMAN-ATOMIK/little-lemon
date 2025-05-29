package com.atomikak.littlelemon

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PreferenceHelper {
    private const val PREF_NAME = "your_app_pref"

    // User login keys
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_FIRST_NAME = "first_name"
    private const val KEY_LAST_NAME = "last_name"
    private const val KEY_EMAIL = "email"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // First time logic
    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_IS_LOGGED_IN, true)
    }

    fun setIsLoggedIn(context: Context, isFirstTime: Boolean) {
        getPreferences(context).edit {
            putBoolean(KEY_IS_LOGGED_IN, isFirstTime)
        }
    }

    // Login logic
    fun saveLoginDetails(context: Context, firstName: String, lastName: String, email: String) {
        getPreferences(context).edit {
            putString(KEY_FIRST_NAME, firstName)
            putString(KEY_LAST_NAME, lastName)
            putString(KEY_EMAIL, email)
            putBoolean(KEY_IS_LOGGED_IN, true)
        }
    }

    fun getFirstName(context: Context): String? {
        return getPreferences(context).getString(KEY_FIRST_NAME, null)
    }

    fun getLastName(context: Context): String? {
        return getPreferences(context).getString(KEY_LAST_NAME, null)
    }

    fun getEmail(context: Context): String? {
        return getPreferences(context).getString(KEY_EMAIL, null)
    }

    fun logout(context: Context) {
        getPreferences(context).edit {
            remove(KEY_FIRST_NAME)
            remove(KEY_LAST_NAME)
            remove(KEY_EMAIL)
            putBoolean(KEY_IS_LOGGED_IN, false)
        }
    }
}
