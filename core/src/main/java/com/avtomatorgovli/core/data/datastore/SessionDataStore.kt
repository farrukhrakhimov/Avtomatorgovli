package com.avtomatorgovli.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val activeUserKey = longPreferencesKey("active_user_id")

    val activeUserId: Flow<Long?> = dataStore.data.map { prefs ->
        prefs[activeUserKey]
    }

    suspend fun setActiveUser(userId: Long) {
        dataStore.edit { prefs ->
            prefs[activeUserKey] = userId
        }
    }

    suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.remove(activeUserKey)
        }
    }
}
