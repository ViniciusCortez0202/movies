package com.stant.movies.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stant.movies.utils.Resource

interface UseCase<Params, Return> {
    fun call(params: Params): LiveData<Resource<Return>>;
}