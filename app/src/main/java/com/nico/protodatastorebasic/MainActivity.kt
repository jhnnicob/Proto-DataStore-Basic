package com.nico.protodatastorebasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import com.nico.protodatastorebasic.datastore.SettingsProtoDataStore
import com.nico.protodatastorebasic.datastore.UserDataProtoDataStore
import com.nico.protodatastorebasic.model.User
import com.nico.protodatastorebasic.ui.theme.ProtoDataStoreBasicTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsProto = SettingsProtoDataStore(applicationContext)
        val userDataProto = UserDataProtoDataStore(applicationContext)

        setContent {
            ProtoDataStoreBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SettingScreen(protoDataStore = settingsProto)
                        UserDataScreen(protoDataStore = userDataProto)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingScreen(
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

@Composable
fun UserDataScreen(
    protoDataStore: UserDataProtoDataStore
) {

    val userData = protoDataStore.userDataList.asLiveData().observeAsState().value

    val username = remember {
        mutableStateOf("")
    }

    val userType = remember {
        mutableStateOf("")
    }

    val token = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { 
                Text(text = "Enter username")
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        TextField(
            value = userType.value,
            onValueChange = {
                userType.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Enter usertype")
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        TextField(
            value = token.value,
            onValueChange = {
                token.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Enter user token")
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        Button(onClick = {
            scope.launch {
                protoDataStore.saveUserDataList(User(username.value, userType.value, token.value))
            }
        }) {
            Text(text = "Save")
        }

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        userData?.map { user ->
            Text(text = "Username: ${user.username}")
            Text(text = "Usertype: ${user.userType}")
            Text(text = "Token: ${user.token}")
        }

    }
}