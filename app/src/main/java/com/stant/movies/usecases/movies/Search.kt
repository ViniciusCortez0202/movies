package com.stant.movies.usecases

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.stant.movies.data.Connection
import com.stant.movies.data.repositories.MoviesRepository
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.MovieEntity
import com.stant.movies.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class Search(
    private var moviesRepository: MoviesRepositoryImpl
) : UseCase<SearchParams, List<MovieEntity>> {
    override fun call(params: SearchParams) = liveData<Resource<List<MovieEntity>>>(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(Resource.success(data = moviesRepository.getSearch(params.search, params.language, params.page)))
        } catch (e: Exception) {
            emit(Resource.error(message = "Filme não encontrado"))
        }
    }
}

data class SearchParams(
    var page: Int,
    var language: String,
    var search: String
)