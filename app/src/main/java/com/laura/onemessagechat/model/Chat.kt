package com.laura.onemessagechat.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat (
    var id: Int = -1,
    var message: String = ""
): Parcelable