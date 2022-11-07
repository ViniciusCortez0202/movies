package com.stant.movies.usecases.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.stant.movies.data.repositories.GenresRepositoryImpl
import com.stant.movies.entities.GenresEntity
import com.stant.movies.usecases.UseCase
import com.stant.movies.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class Genres (
    private var genresRepository: GenresRepositoryImpl
    ) : UseCase<GenreParams, List<GenresEntity>>, ViewModel() {
    override fun call(params: GenreParams) = liveData<Resource<List<GenresEntity>>>(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(Resource.success(data = genresRepository.getlist(params.language)))
        } catch (e: Exception) {
            emit(Resource.error(message = "Erro ao buscar detalhes do filme"))
        }
    }
}

data class GenreParams(
    var language: String
)