package com.nico.protodatastorebasic.datastore.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.nico.protodatastorebasic.UserDataList
import java.io.InputStream
import java.io.OutputStream

object UserDataListSerializer : Serializer<UserDataList> {
    override val defaultValue: UserDataList
        get() = UserDataList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDataList {
        try {
            return UserDataList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserDataList, output: OutputStream) {
        t.writeTo(output)
    }

    val Context.userDataListDataStore: DataStore<UserDataList> by dataStore(
        fileName = "user_data_list.pb",
        serializer = UserDataListSerializer
    )
}