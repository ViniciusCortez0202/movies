package com.stant.movies.views.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.stant.movies.entities.GenresEntity
import com.stant.movies.entities.MovieEntity
import com.stant.movies.usecases.genres.Genres
import com.stant.movies.utils.Resource
import com.stant.movies.utils.Status
import views.R


class MoviesAdapter(context: Context, movies: MutableList<MovieEntity>, genres: List<GenresEntity>) : BaseAdapter() {

    private val mContext: Context
    private val movies: MutableList<MovieEntity>
    private val genres: List<GenresEntity>

    init {
        mContext = context
        this.movies = movies
        this.genres = genres
    }

    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(position: Int): Any {
        return ""
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMovie = layoutInflater.inflate(R.layout.row_movie, viewGroup, false)


        val nameView = rowMovie.findViewById<TextView>(R.id.nameMovieRow)
        val dateReleaseView = rowMovie.findViewById<TextView>(R.id.dateReleaseRow)
        val imgView = rowMovie.findViewById<ImageView>(R.id.postMovieRow)
        val genresView = rowMovie.findViewById<TextView>(R.id.genresRow)

        nameView.text = movies[position].titleFromLanguage
        dateReleaseView.text = movies[position].releaseDate
        println(genres.filter { movies[position].genres.contains(it.id) })
        genresView.text = (genres.filter { movies[position].genres.contains(it.id) }).joinToString { ", "; it.name }
        imgView.setImageURI(Uri.parse("https://image.tmdb.org/t/p/original${movies[position].img}"))

        return rowMovie
    }

}