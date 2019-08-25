package com.github.pedrodimoura.news.articles.domain.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.pedrodimoura.news.common.util.readStringOrEmpty

data class Source(
    val id: String = "",
    val name: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }
    }

}