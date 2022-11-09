package com.stant.movies.usecases


import androidx.lifecycle.liveData
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.MovieEntity
import com.stant.movies.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class Getlist(
    private var moviesRepository: MoviesRepositoryImpl
) : UseCase<GetlistParams, List<MovieEntity>> {
    override fun call(params: GetlistParams) = liveData<Resource<List<MovieEntity>>>(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(Resource.success(data = moviesRepository.getlist(params.language, params.page)))
        } catch (e: Exception) {
            emit(Resource.error(message = "Não foi possível buscar os filmes"))
        }
    }
}

data class GetlistParams(val language: String, val page: Int)