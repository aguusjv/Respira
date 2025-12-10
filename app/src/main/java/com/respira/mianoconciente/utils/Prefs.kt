package com.respira.mianoconciente.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("respira_prefs", Context.MODE_PRIVATE)

    fun setOnboardingSeen(seen: Boolean) {
        prefs.edit().putBoolean(KEY_ONBOARDING, seen).apply()
    }

    fun hasSeenOnboarding(): Boolean = prefs.getBoolean(KEY_ONBOARDING, false)

    companion object {
        private const val KEY_ONBOARDING = "onboarding_seen"
    }
}
