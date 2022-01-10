package com.nico.protodatastorebasic.presentation.user_data

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import com.nico.protodatastorebasic.MainViewModel
import com.nico.protodatastorebasic.datastore.UserDataProtoDataStore
import kotlinx.coroutines.launch

@Composable
fun UserDataScreen(
    viewModel: MainViewModel,
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                viewModel.saveUserData(
                    username = username.value,
                    userType = userType.value,
                    token = token.value
                )
            }) {
                Text(text = "Save")
            }
            Button(onClick = {
                scope.launch {
                    viewModel.deleteList()
                }
            }) {
                Text(text = "Delete")
            }
        }


        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        userData?.map { user ->

            Text(text = "Username: ${user.username}")
            Text(text = "Usertype: ${user.userType}")
            Text(text = "Token: ${user.token}")
        }

    }
}