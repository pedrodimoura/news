package com.github.pedrodimoura.news.articles.presentation.entity

import android.os.Parcel
import android.os.Parcelable
import com.github.pedrodimoura.news.common.util.readNonNullDate
import com.github.pedrodimoura.news.common.util.readStringOrEmpty
import com.github.pedrodimoura.news.common.util.writeNonNullDate
import java.util.Date

data class ArticleView(
    val title: String,
    val description: String,
    val content: String,
    val publishedAt: Date,
    val urlToImage: String,
    val url: String,
    val sourceName: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readNonNullDate(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeString(content)
        dest.writeNonNullDate(publishedAt)
        dest.writeString(urlToImage)
        dest.writeString(url)
        dest.writeString(sourceName)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ArticleView> {

        const val EXTRA_KEY = "article_view_key"

        override fun createFromParcel(parcel: Parcel): ArticleView {
            return ArticleView(parcel)
        }

        override fun newArray(size: Int): Array<ArticleView?> {
            return arrayOfNulls(size)
        }
    }

}