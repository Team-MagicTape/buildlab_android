package com.narsha.datastore.tokenManager

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


object TokenManager {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val Context.dataStore by preferencesDataStore(name = "token")
    private val dataStore get() = appContext.dataStore

    private val REF_TOKEN = stringPreferencesKey("ref_token")
    private val ACC_TOKEN = stringPreferencesKey("acc_token")

    fun saveRefToken(token: String?) {
        runBlocking {
            try {
                appContext.dataStore.edit { preferences ->
                    if (token != null) preferences[REF_TOKEN] = token
                    else preferences.remove(REF_TOKEN)
                }
            } catch (e: Exception) {
                Log.d("토큰오류", "saveRefToken: $e")
            }
        }
    }

    fun saveAccToken(token: String?) {
        runBlocking {
            try {
                appContext.dataStore.edit { preferences ->
                    if (token != null) preferences[ACC_TOKEN] = token
                    else preferences.remove(ACC_TOKEN)
                }
            } catch (e: Exception) {
                Log.d("토큰오류", "saveAccToken: $e")
            }
        }
    }

    fun getRefToken(): String? {
        return runBlocking {
            appContext.dataStore.data.first()[REF_TOKEN]
        }
    }

    fun getAccToken(): String? {
        return runBlocking {
            appContext.dataStore.data.first()[ACC_TOKEN]
        }
    }

    fun clearToken() {
        runBlocking {
            appContext.dataStore.edit { preferences ->
                preferences.remove(REF_TOKEN)
                preferences.remove(ACC_TOKEN)
            }
        }
    }
}
