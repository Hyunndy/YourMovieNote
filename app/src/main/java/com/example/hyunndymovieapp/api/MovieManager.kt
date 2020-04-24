package com.example.hyunndymovieapp.api

import io.reactivex.Observable
import java.util.*

class MovieManager(private val api: RestApi = RestApi()) {
    suspend fun getMovieList(param: Map<String, String>): MovieList { // (1) 매개변수 변경
        val result = api.getMovieListCo(param) // (2) REST API의 코루틴 버전
        return process(result)
    }

    private fun process(response: MovieListResponse): MovieList {
        val list = response.results.map {
            MovieItem(
                it.vote_count,
                it.vote_average,
                it.title,
                it.release_date,
                it.poster_path,
                it.overview
            )
        }
        return MovieList(response.page, list)
    }
}