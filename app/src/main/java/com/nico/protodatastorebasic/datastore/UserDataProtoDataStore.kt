package com.nico.protodatastorebasic.datastore

import android.content.Context
import com.nico.protodatastorebasic.UserData
import com.nico.protodatastorebasic.datastore.serializer.UserDataListSerializer.userDataListDataStore
import com.nico.protodatastorebasic.datastore.serializer.UserDataSerializer.userDataDataStore
import com.nico.protodatastorebasic.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataProtoDataStore(
    private val context: Context
) {

    val userData: Flow<User> = context.userDataDataStore.data
        .map { currentUserData ->
            User(
                username = currentUserData.username,
                userType = currentUserData.userType,
                token = currentUserData.token
            )
        }

    val userDataList: Flow<List<User>> = context.userDataListDataStore.data
        .map { currentUserDataList ->
            currentUserDataList.userDataList.map {
                User(
                    username = it.username,
                    userType = it.userType,
                    token = it.token
                )
            }
        }

    suspend fun saveUserData(user: User) {
        context.userDataDataStore.updateData { userData ->
            userData.toBuilder()
                .setUsername(user.username)
                .setUserType(user.userType)
                .setToken(user.token)
                .build()
        }
    }

    suspend fun save(user: User) {
        context.userDataListDataStore.updateData { userData ->
            userData.toBuilder()
                .addUserData(user.toUserData())
                .build()
        }
    }

    private fun User.toUserData() : UserData {
        return UserData.newBuilder()
            .setUsername(this.username)
            .setUserType(this.userType)
            .setToken(this.token)
            .build()
    }

    suspend fun delete() {
        context.userDataListDataStore.updateData { userDataList ->
            userDataList.toBuilder().clear().build()
        }
    }
}