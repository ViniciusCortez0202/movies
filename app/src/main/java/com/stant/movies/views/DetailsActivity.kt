package com.stant.movies.views

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.stant.movies.data.RetrofitConnection
import com.stant.movies.data.repositories.GenresRepositoryImpl
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.GenresEntity
import com.stant.movies.entities.MovieEntity
import com.stant.movies.model.GenresModel
import com.stant.movies.model.MovieModel
import com.stant.movies.usecases.Details
import com.stant.movies.usecases.DetailsParams
import com.stant.movies.usecases.Getlist
import com.stant.movies.usecases.Search
import com.stant.movies.usecases.genres.GenreParams
import com.stant.movies.usecases.genres.Genres
import com.stant.movies.utils.Status
import com.stant.movies.views.adapters.MoviesAdapter
import views.R

class DetailsActivity(

) : AppCompatActivity() {

    private var connection: RetrofitConnection
    private var moviesRepositoryImpl: MoviesRepositoryImpl
    private var genreRepositoryImpl: GenresRepositoryImpl
    private var details: Details
    private val genres: Genres

    init {
        connection = RetrofitConnection()
        moviesRepositoryImpl= MoviesRepositoryImpl(connection, MovieModel())
        genreRepositoryImpl = GenresRepositoryImpl(connection, GenresModel())
        details = Details(moviesRepositoryImpl)
        genres = Genres(genreRepositoryImpl)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        var id = intent.getIntExtra("id", 0)
        getDetails(id)
    }

    private fun getDetails(id: Int){
        details.call(DetailsParams( id, "pt-BR")).observe(this, Observer {
            it?.let {
                    resource -> when(resource.status) {
                Status.SUCCESS -> {
                    val movie: MovieEntity = resource.data!!
                    seValues(movie)
                }
                Status.ERROR -> {
                    println("erro")
                }
                Status.LOADING -> {
                    println("carregando")
                }
            }
            }
        })
    }

    private fun seValues(movie: MovieEntity){

        val imgView = findViewById<ImageView>(R.id.main_image_details)
        val imgCompanyView = findViewById<ImageView>(R.id.company_image_details)
        val rateView = findViewById<TextView>(R.id.rate_details)
        val dateReleaseView = findViewById<TextView>(R.id.date_release_details)
        val genresAndLanguageView = findViewById<TextView>(R.id.genres_language_movie_details)
        val companyNameView = findViewById<TextView>(R.id.company_name_details)
        val movieNameView = findViewById<TextView>(R.id.name_movie_details)
        val overviewView = findViewById<TextView>(R.id.overview)


        imgView.setImageURI(Uri.parse("https://image.tmdb.org/t/p/original${movie.img}"))
        imgCompanyView.setImageURI(Uri.parse("https://image.tmdb.org/t/p/original${movie.logoCompany}"))

        rateView.text = movie.popularity.toString()
        dateReleaseView.text = movie.releaseDate
        genresAndLanguageView.text = "${movie.genresNames}\n\nIdioma original: ${movie.language}"
        companyNameView.text = movie.nameCompany
        movieNameView.text = movie.title
        overviewView.text = movie.overView
    }
}