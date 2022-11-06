package com.stant.movies.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.MovieEntity
import com.stant.movies.utils.Resource
import kotlinx.coroutines.Dispatchers

import java.lang.Exception


class Details(
    private var moviesRepository: MoviesRepositoryImpl
) :  UseCase<DetailsParams, MovieEntity>, ViewModel() {

    override fun call(params: DetailsParams) = liveData<Resource<MovieEntity>>(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(Resource.success(data = moviesRepository.getDetails(params.id, params.language)))
        } catch (e: Exception) {
            emit(Resource.error(message = "Erro ao buscar detalhes do filme"))
        }
    }

}

data class DetailsParams(
    var id: Int,
    var language: String
)