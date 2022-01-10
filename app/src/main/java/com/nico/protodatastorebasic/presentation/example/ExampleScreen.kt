package com.nico.protodatastorebasic.presentation.example

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import com.nico.protodatastorebasic.datastore.SettingsProtoDataStore
import kotlinx.coroutines.launch


@Composable
fun ExampleScreen(
    protoDataStore: SettingsProtoDataStore
) {

    val counter = protoDataStore.counterFlow.asLiveData().observeAsState().value.toString()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            scope.launch {
                protoDataStore.incrementCounter()
            }
        }) {
            Text(text = "Increment")
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(text = counter)

        Spacer(modifier = Modifier.padding(vertical = 20.dp))
    }
}