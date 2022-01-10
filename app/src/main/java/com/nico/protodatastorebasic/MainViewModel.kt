package com.nico.protodatastorebasic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nico.protodatastorebasic.datastore.UserDataProtoDataStore
import com.nico.protodatastorebasic.model.User
import kotlinx.coroutines.launch

class MainViewModel(
    private val protoDataStore: UserDataProtoDataStore
) : ViewModel() {

    fun deleteList() {
        viewModelScope.launch {
            protoDataStore.delete()
        }
    }
    fun saveUserData(username: String, userType: String, token: String) {
        val user = User(username = username, userType = userType, token = token)
        viewModelScope.launch {
            protoDataStore.save(user = user)
        }
    }
}

