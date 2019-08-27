package com.github.pedrodimoura.news.articles.domain.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.pedrodimoura.news.common.util.readNonNullDate
import com.github.pedrodimoura.news.common.util.readStringOrEmpty
import com.github.pedrodimoura.news.common.util.writeNonNullDate
import java.util.Date

data class Article(
    val id: Int = 0,
    val source: Source = Source(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: Date = Date(),
    val content: String = ""
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Source::class.java.classLoader)!!,
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readNonNullDate(),
        parcel.readStringOrEmpty()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeParcelable(source, 0)
        dest.writeString(author)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeString(url)
        dest.writeString(urlToImage)
        dest.writeNonNullDate(publishedAt)
        dest.writeString(content)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }

}