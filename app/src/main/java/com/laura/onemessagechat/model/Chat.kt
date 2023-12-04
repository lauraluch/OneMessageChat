package com.laura.onemessagechat.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Chat (
    @PrimaryKey
    var id: String = "",
    @NonNull
    var message: String = ""
): Parcelable