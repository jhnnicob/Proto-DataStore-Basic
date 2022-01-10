package com.nico.protodatastorebasic.datastore

import android.content.Context
import com.nico.protodatastorebasic.datastore.serializer.SettingsSerializer.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsProtoDataStore(
    private val context: Context
) {

    val counterFlow: Flow<Int> = context.settingsDataStore.data
        .map { setting ->
            setting.exampleCounter
        }

    suspend fun incrementCounter() {
        context.settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setExampleCounter(currentSettings.exampleCounter + 1)
                .build()
        }
    }
}