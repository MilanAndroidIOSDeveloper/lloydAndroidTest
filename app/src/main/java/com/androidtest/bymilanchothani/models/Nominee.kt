package com.androidtest.bymilanchothani.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nominees")
data class Nominee(
    @SerializedName("nominee_id") @PrimaryKey val nomineeId: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String)
