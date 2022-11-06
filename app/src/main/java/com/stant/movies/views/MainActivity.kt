package com.stant.movies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.stant.movies.data.RetrofitConnection
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.model.MovieModel
import com.stant.movies.usecases.*
import com.stant.movies.utils.Status
import views.R
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callLists()
    }


     fun callLists(){
        val connection = RetrofitConnection()
        var movieModel = MovieModel()
        val view = findViewById<TextView>(R.id.textView)
        try {
            var moviesRepositoryImpl = MoviesRepositoryImpl(connection, movieModel)
            /*val movies = Getlist(moviesRepositoryImpl)
            movies.call(GetlistParams( "pt-BR", 1)).observe(this, Observer {
                it?.let {
                        resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            view.text = resource.data?.last()?.title
                        }
                        Status.ERROR -> {
                            view.text = "erro"
                        }
                        Status.LOADING -> {
                            view.text = "carregando"
                        }
                    }
                }
            })*/

            /*val search = Search(moviesRepositoryImpl)
            search.call(SearchParams( 1, "pt-BR", "a ressaca")).observe(this, Observer {
                it?.let {
                        resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            println(resource.data)
                            view.text = resource.data?.first()?.title
                        }
                        Status.ERROR -> {
                            view.text = "erro"
                        }
                        Status.LOADING -> {
                            view.text = "carregando"
                        }
                    }
                }
            })*/

            /*val details = Details(moviesRepositoryImpl)
            details.call(DetailsParams( 99861, "pt-BR")).observe(this, Observer {
                it?.let {
                    resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            view.text = resource.data?.title
                        }
                        Status.ERROR -> {
                            view.text = "erro"
                        }
                        Status.LOADING -> {
                            view.text = "carregando"
                        }
                    }
                }
            })*/

        } catch (e: Exception){
            println(e.message)
        }
    }

}