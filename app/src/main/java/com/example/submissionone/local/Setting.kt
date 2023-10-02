package com.example.submissionone.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")
class Setting constructor(context: Context) {
    private val settingsDataStore = context.prefDataStore
    private val key = booleanPreferencesKey("theme_setting")

    fun getTemaSetting(): Flow<Boolean> =
        settingsDataStore.data.map { preferences -> preferences[key] ?: false }

    suspend fun saveTemaSetting(isDarkModeActive: Boolean) {
        settingsDataStore.edit { preferences -> preferences[key] = isDarkModeActive }
    }
}