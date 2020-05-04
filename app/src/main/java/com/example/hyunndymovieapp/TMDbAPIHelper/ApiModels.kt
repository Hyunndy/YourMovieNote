package com.example.hyunndymovieapp.TMDbAPIHelper

/*
REST API 응답에 사용할 클래스들

 */


// result 배열을 담음.
class MovieListResponse(
    var page:Int,
    val results:  List<MovieDetailResponse>
)

// results 하나.
class MovieDetailResponse(
    val vote_count:Int,
    val vote_average: Float = 0.0F,
    val title: String,
    val release_date: String,
    val poster_path: String?,
    val overview: String?,
    val genre_ids: List<Int>?
) {
}