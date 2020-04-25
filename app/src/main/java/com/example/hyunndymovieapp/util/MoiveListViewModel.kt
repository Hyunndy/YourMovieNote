package com.example.hyunndymovieapp.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hyunndymovieapp.api.MovieList

class MovieListViewModel : ViewModel() {

    private var theMovieList : MovieList? = null

    fun getMovieList() : MovieList? = theMovieList

    private fun loadMovieList() {

    }

}