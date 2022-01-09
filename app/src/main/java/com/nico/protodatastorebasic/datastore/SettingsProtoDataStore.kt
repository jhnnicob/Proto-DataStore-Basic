package com.nico.protodatastorebasic.datastore

import android.content.Context
import com.nico.protodatastorebasic.datastore.SettingsSerializer.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SettingsProtoDataStore(
    private val context: Context
) {

    val counterFlow: Flow<Int> = context.settingsDataStore.data
        .map { setting ->
            setting.exampleCounter
        }

    val counter = runBlocking { context.settingsDataStore.data.first() }

    suspend fun incrementCounter() {
        context.settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setExampleCounter(currentSettings.exampleCounter + 1)
                .build()
        }
    }
}