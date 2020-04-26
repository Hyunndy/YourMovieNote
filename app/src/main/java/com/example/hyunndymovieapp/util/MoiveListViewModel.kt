package com.example.hyunndymovieapp.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.api.MovieManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieListViewModel : ViewModel() {
    private var movieList: MutableLiveData<MovieList>? = MutableLiveData()
    private val movieManager by lazy { MovieManager() }
    private var selectedIdx : Int = 0

    init {
        loadMovieList()
    }


    fun getMovieList(): MutableLiveData<MovieList>? = movieList
    fun getSelectedIdx() : Int = selectedIdx
    fun setSelectedIdx(index : Int) {
        selectedIdx = index
    }


    fun loadMovieList() {

        val now = LocalDate.now()
        val nowstr = now.format(DateTimeFormatter.ISO_DATE)
        val prevMonth = now.minusMonths(1)
        val prevstr = prevMonth.format(DateTimeFormatter.ISO_DATE)
        // (1) 코루틴의 launch 빌더

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val param = mapOf(
                    "page" to "1",
                    "api_key" to API_KEY,
                    //"primary_release_date.gte" to prevstr,
                    //"primary_release_date.lte" to nowstr,
                    "language" to "ko",
                    "sort_by" to "popularity.desc"
                )

                Log.d("TEST3", "에엥 데이터로드")
                val retrivedMovie = movieManager.getMovieList(param)
                retrivedMovie.page = retrivedMovie.page?.plus(1)

                movieList?.value = retrivedMovie

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TEST2", "오류납니다.")
            }
        }
    }
}