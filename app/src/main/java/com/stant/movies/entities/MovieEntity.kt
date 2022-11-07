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
    @SerializedName("vote_average")
    var popularity: Double,
    @SerializedName("poster_path")
    var img: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("original_language")
    var language: String,
    @SerializedName("genre_ids")
    var genres: List<Int>,
    var nameCompany: String,
    var logoCompany: String,
    var genresNames: String
)
