package com.nico.protodatastorebasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nico.protodatastorebasic.datastore.SettingsProtoDataStore
import com.nico.protodatastorebasic.datastore.UserDataProtoDataStore
import com.nico.protodatastorebasic.presentation.example.ExampleScreen
import com.nico.protodatastorebasic.presentation.ui.theme.ProtoDataStoreBasicTheme
import com.nico.protodatastorebasic.presentation.user_data.UserDataScreen

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
                        ExampleScreen(protoDataStore = settingsProto)
                        UserDataScreen(viewModel = mainViewModel, protoDataStore = userDataProto)
                    }
                }
            }
        }
    }

    private fun createWithFactory(
        create: () -> ViewModel
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return create.invoke() as T
            }
        }
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            createWithFactory {
                MainViewModel(protoDataStore = UserDataProtoDataStore(applicationContext))
            }
        )[MainViewModel::class.java]
    }

}
