package edu.uw.jadedsou.mediabrowser
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    @Json(name = "original_title")
    val title: String,
    @Json(name="poster_path")
    val posterPath: String,
    @Json(name = "overview")
    val plot: String
) : Parcelable
