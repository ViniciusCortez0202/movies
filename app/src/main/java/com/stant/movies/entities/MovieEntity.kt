package com.stant.movies.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieEntity (

    @PrimaryKey @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val title:String,
    @SerializedName("title")
    val titleFromLanguage:String,
    @SerializedName("overview")
    var overView: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var img: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("original_language")
    var language: String,
    @Ignore
    @SerializedName("genre_ids")
    var genres: List<Int>,
    var nameCompany: String,
    var logoCompany: String,
    var genresNames: String
)
