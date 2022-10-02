package com.tunanh.clicktofood_hilt.data.local

import android.content.SharedPreferences
import com.tunanh.clicktofood_hilt.util.AppConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(private val preferences: SharedPreferences) {
    fun setIntro(intro: Boolean) {
        preferences.edit().putBoolean(AppConfig.Preferences.INTRO, intro).apply()
    }

    fun getIntro(): Boolean {
        return preferences.getBoolean(AppConfig.Preferences.INTRO, false)
    }


    fun setEmail(email: String) {
        preferences.edit().putString(AppConfig.Preferences.EMAIL, email).apply()
    }

    fun getEmail(): String {
        return preferences.getString(AppConfig.Preferences.EMAIL, "").toString()
    }

    fun setToken(token: String) {
        preferences.edit().putString(AppConfig.Preferences.TOKEN, token).apply()
    }

    fun getToken(): String {
        return preferences.getString(AppConfig.Preferences.TOKEN, "").toString()
    }
}