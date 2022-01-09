package com.nico.protodatastorebasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import com.nico.protodatastorebasic.datastore.SettingsProtoDataStore
import com.nico.protodatastorebasic.ui.theme.ProtoDataStoreBasicTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsProto = SettingsProtoDataStore(applicationContext)

        setContent {
            ProtoDataStoreBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SettingScreen(settingsProto)
                }
            }
        }
    }
}

@Composable
fun SettingScreen(
    settingsProto: SettingsProtoDataStore
) {

    val counter = settingsProto.counterFlow.asLiveData().observeAsState().value.toString()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            scope.launch {
                settingsProto.incrementCounter()
            }
        }) {
            Text(text = "Increment")
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(text = counter)
    }
}