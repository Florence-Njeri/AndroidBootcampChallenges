package com.florencenjeri.listmaker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskList(val name: String, val tasks: ArrayList<String> = ArrayList()) : Parcelable