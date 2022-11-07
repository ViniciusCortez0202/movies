package com.stant.movies.entities

import com.google.gson.annotations.SerializedName

data class MovieEntity (
    @SerializedName("id")
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
    @SerializedName("genre_ids")
    var genres: List<Int>,
)