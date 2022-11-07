package com.stant.movies.entities

import com.google.gson.annotations.SerializedName

data class GenresEntity(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
) {
}