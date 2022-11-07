package com.stant.movies.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stant.movies.data.RetrofitConnection
import com.stant.movies.data.repositories.GenresRepositoryImpl
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.GenresEntity
import com.stant.movies.entities.MovieEntity
import com.stant.movies.model.GenresModel
import com.stant.movies.model.MovieModel
import com.stant.movies.usecases.Getlist
import com.stant.movies.usecases.Search
import com.stant.movies.usecases.SearchParams
import com.stant.movies.usecases.genres.GenreParams
import com.stant.movies.usecases.genres.Genres
import com.stant.movies.utils.Status
import com.stant.movies.views.adapters.MoviesAdapter
import views.R

class SearchActivity : AppCompatActivity() {


    private var connection: RetrofitConnection
    private var moviesRepositoryImpl: MoviesRepositoryImpl
    private var genreRepositoryImpl: GenresRepositoryImpl
    private var getList: Getlist
    private var search: Search
    private val genres: Genres
    private var moviesData: MutableList<MovieEntity> = mutableListOf()
    private var genresData: MutableList<GenresEntity> = mutableListOf()
    private var pageListMoviesControl: Int = 1
    private var adapter = MoviesAdapter(this, moviesData, genresData)
    private lateinit var searchValue: String

    init {
        connection = RetrofitConnection()
        moviesRepositoryImpl= MoviesRepositoryImpl(connection, MovieModel())
        genreRepositoryImpl = GenresRepositoryImpl(connection, GenresModel())
        getList = Getlist(moviesRepositoryImpl)
        search = Search(moviesRepositoryImpl)
        genres = Genres(genreRepositoryImpl)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchValue = intent.getStringExtra("query")!!
        title = searchValue


        getGenres()
        listInit()
    }

    private fun listInit(){
        var listView = findViewById<ListView>(R.id.search_list)
        listView.adapter = adapter
        setOnScrollListening(listView)
        setOnClickItemListView(listView)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setOnScrollListening(listView: ListView){
        listView.setOnScrollListener(object: AbsListView.OnScrollListener {
            //Confere se a página realmente precisa mudar
            private var pageIndex = 1

            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
            }

            override fun onScroll(p0: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                var indicator = findViewById<ProgressBar>(R.id.search_indicator)

                if(firstVisibleItem+visibleItemCount >= totalItemCount && totalItemCount != 0 &&  pageIndex != pageListMoviesControl) {
                    getListMoviesSearched()
                    pageIndex++
                }
                when(pageIndex == pageListMoviesControl){
                    true -> indicator.visibility = View.VISIBLE
                    false -> indicator.visibility = View.INVISIBLE
                }
            }

        })
    }


    private fun setOnClickItemListView(listView: ListView){
        listView.setOnItemClickListener { p0, p1, position, p3 ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", moviesData[position].id)
            intent.putExtra("name", moviesData[position].titleFromLanguage)
            startActivity(intent)
        }
    }

    fun getListMoviesSearched(){
        search.call(SearchParams( pageListMoviesControl, "pt-BR", searchValue)).observe(this, Observer {
            it?.let {
                    resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        moviesData.addAll(moviesData.size, resource.data!!.toMutableList())
                        adapter.notifyDataSetChanged()
                        pageListMoviesControl++
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

    private fun getGenres() {
        genres.call(GenreParams( "pt-BR")).observe(this, Observer {
            it?.let {
                    resource -> when(resource.status) {
                Status.SUCCESS -> {
                    genresData.addAll(resource.data!!)
                    getListMoviesSearched() // Garante que só pega os filmes quando tiver os gêneros
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
}