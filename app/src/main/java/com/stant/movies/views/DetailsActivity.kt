package com.stant.movies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.stant.movies.data.RetrofitConnection
import com.stant.movies.data.RoomConnection
import com.stant.movies.data.local.repositories.MoviesLocalRepositoryImpl
import com.stant.movies.data.repositories.GenresRepositoryImpl
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.MovieEntity
import com.stant.movies.model.GenresModel
import com.stant.movies.model.MovieModel
import com.stant.movies.usecases.Details
import com.stant.movies.usecases.DetailsParams
import com.stant.movies.usecases.genres.Genres
import com.stant.movies.utils.DateFormat
import com.stant.movies.utils.Status
import views.R
import java.lang.Exception

class DetailsActivity(

) : AppCompatActivity() {

    private var connection: RetrofitConnection
    private var localConnetion: RoomConnection
    private var moviesLocalRepositoryImpl: MoviesLocalRepositoryImpl
    private var moviesRepositoryImpl: MoviesRepositoryImpl
    private var genreRepositoryImpl: GenresRepositoryImpl
    private var details: Details
    private val genres: Genres
    private val progressIndicator: ProgressIndicator

    init {
        connection = RetrofitConnection()
        localConnetion = RoomConnection()
        moviesLocalRepositoryImpl = MoviesLocalRepositoryImpl(localConnetion, this)
        moviesRepositoryImpl= MoviesRepositoryImpl(connection, MovieModel())
        genreRepositoryImpl = GenresRepositoryImpl(connection, GenresModel())
        details = Details(moviesRepositoryImpl, moviesLocalRepositoryImpl)
        genres = Genres(genreRepositoryImpl)
        progressIndicator = ProgressIndicator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressIndicator.startLoading()
        var id = intent.getIntExtra("id", 0)
        var name = intent.getStringExtra("name")
        title = name
        getDetails(id)

    }

    fun setMovieFromLocal(movie: MovieEntity){
        seValues(movie)
        restorePageState()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getDetails(id: Int){
        details.call(DetailsParams( id, "pt-BR")).observe(this, Observer {
            it?.let {
                    resource -> when(resource.status) {
                Status.SUCCESS -> {
                    val movie: MovieEntity = resource.data!!
                    seValues(movie)
                    restorePageState()
                    insertMovieInLocal(movie)
                }
                Status.ERROR -> {
                    println("erro")
                }
                Status.LOADING -> {


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

        Picasso.get().load("https://image.tmdb.org/t/p/original${movie.img}").into(imgView)
        Picasso.get().load("https://image.tmdb.org/t/p/original${movie.logoCompany}").into(imgCompanyView)

        rateView.text = movie.popularity.toString()
        dateReleaseView.text = DateFormat.getDateInAnotherFormat("yyyy-MM-dd", "dd/MM/yyyy", movie.releaseDate)
        genresAndLanguageView.text = "${movie.genresNames}\n\nIdioma original: ${movie.language}"
        companyNameView.text = movie.nameCompany
        movieNameView.text = movie.titleFromLanguage
        overviewView.text = movie.overView
    }


    fun restorePageState(){
        findViewById<ConstraintLayout>(R.id.details).visibility = View.VISIBLE
        progressIndicator.close()
    }

    fun insertMovieInLocal(movie: MovieEntity){
        try{
            Thread {
                moviesLocalRepositoryImpl.insert(movie)
            }.start()
        } catch (e: Exception) {
            println(e.message)
        }
    }

}