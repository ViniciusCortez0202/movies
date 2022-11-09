package com.stant.movies.data

interface Connection<Library> {
    fun connect(vararg info: Any?): Library
}