package com.example.hyunndymovieapp.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.api.MovieManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieListViewModel : ViewModel() {
    private var movieList: MutableLiveData<MovieList>? = MutableLiveData()
    private val movieManager by lazy { MovieManager() }

    init {
        loadMovieList()
    }

    fun getMovieList(): MutableLiveData<MovieList>? = movieList

    private fun loadMovieList() {

        // (1) 코루틴의 launch 빌더
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val param = mapOf(
                    "page" to "1",
                    "api_key" to API_KEY,
                    "language" to "ko",
                    "sort_by" to "popularity.desc"
                )

                val retrivedMovie = movieManager.getMovieList(param)
                retrivedMovie.page = retrivedMovie.page?.plus(1)

                movieList?.value = retrivedMovie

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}