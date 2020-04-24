package com.example.hyunndymovieapp.api

/*
REST API 응답에 사용할 클래스들

API 키 b9e3a985b4c7aebdc3698d3f55d45d0e

https://www.themoviedb.org/documentation/api/discover


JSON파일이 오는 URL
https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=b9e3a985b4c7aebdc3698d3f55d45d0e&language=ko&page=1

 */


// result 배열을 담음.
class MovieListResponse(
    var page:Int,
    val results:  List<MovieDetailResponse>
)

// results 하나.
class MovieDetailResponse(
    val vote_count:Int,
    val vote_average: Float,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String?
)