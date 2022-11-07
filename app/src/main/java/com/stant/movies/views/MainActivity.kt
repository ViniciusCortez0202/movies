package com.stant.movies.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import com.stant.movies.data.RetrofitConnection
import com.stant.movies.data.repositories.GenresRepositoryImpl
import com.stant.movies.data.repositories.MoviesRepositoryImpl
import com.stant.movies.entities.GenresEntity
import com.stant.movies.entities.MovieEntity
import com.stant.movies.model.GenresModel
import com.stant.movies.model.MovieModel
import com.stant.movies.usecases.*
import com.stant.movies.usecases.genres.GenreParams
import com.stant.movies.usecases.genres.Genres
import com.stant.movies.utils.Status
import com.stant.movies.views.adapters.MoviesAdapter
import views.R

class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)

        getGenres()
        listInit()
    }

    private fun listInit(){
        var listView = findViewById<ListView>(R.id.movies_list)
        listView.adapter = adapter
        setOnScrolListening(listView)
        setOnClickItemListView(listView)
    }

    private fun setOnScrolListening(listView: ListView){
        listView.setOnScrollListener(object: AbsListView.OnScrollListener {
            //Confere se a página realmente precisa mudar
            private var pageIndex = 1

            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
            }

            override fun onScroll(p0: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                var indicator = findViewById<ProgressBar>(R.id.indicator)

                if(firstVisibleItem+visibleItemCount >= totalItemCount && totalItemCount != 0 &&  pageIndex != pageListMoviesControl) {
                    getListMovies()
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
        listView.setOnItemClickListener { p0, p1, p2, p3 ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", moviesData[p2].id)
            startActivity(intent)
        }
    }

    fun getListMovies(){
        getList.call(GetlistParams( "pt-BR", pageListMoviesControl)).observe(this, Observer {
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
                        getListMovies() // Garante que só pega os filmes quando tiver os gêneros
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

     fun callLists(){

        var movieModel = MovieModel()


            var moviesRepositoryImpl = MoviesRepositoryImpl(connection, movieModel)


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


           /* val details = Genres(genreRepositoryImpl)
            details.call(GenreParams( "pt-BR")).observe(this, Observer {
                it?.let {
                    resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            view.text = resource.data?.first()?.name
                        }
                        Status.ERROR -> {
                            view.text = "erro"
                        }
                        Status.LOADING -> {
                            view.text = "carregando"
                        }
                    }
                }
            })
        } catch (e: Exception){
            println(e.message)
        }*/
    }

}