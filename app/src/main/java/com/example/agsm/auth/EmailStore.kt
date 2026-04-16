package com.example.agsm.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.emailStore by preferencesDataStore("email_store")

object EmailHistory {
    private val EMAILS_KEY = stringPreferencesKey("emails")

    suspend fun saveEmail(context: Context, email: String) {
        context.emailStore.edit { prefs ->
            val list = prefs[EMAILS_KEY]?.split(";")?.toMutableList() ?: mutableListOf()
            if(!list.contains(email)) {
                list.add(email)
                prefs[EMAILS_KEY] = list.joinToString(";")
            }
        }
    }

    fun getEmails(context: Context) =
        context.emailStore.data.map { prefs ->
            prefs[EMAILS_KEY]?.split(";") ?: emptyList()
        }
}