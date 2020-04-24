package com.example.hyunndymovieapp.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
각 영화 아이템을 위한 데이터 클래스 정의

@Parcelize 어노테이션
Parcelable 기본형태를 써도됨.
 */

@Parcelize
data class MovieItem(
    val vote_count: Int,
    val vote_average: Float,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String?
) : Parcelable {

}

@Parcelize
data class MovieList(
    var page: Int?,
    val results: List<MovieItem>) : Parcelable {}