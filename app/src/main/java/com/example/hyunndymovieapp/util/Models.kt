package com.example.hyunndymovieapp.util

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
    val poster_path: String?,
    val overview: String?,
    val genre_ids : List<Int>?
) : Parcelable {}

@Parcelize
data class MovieList(
    var page: Int?,
    val results: List<MovieItem>) : Parcelable {}

val MovieGenre = hashMapOf(28 to "액션", 12 to "모험", 16 to "애니메이션", 35 to "코미디", 80 to "범죄", 99 to "다큐멘터리", 18 to "드라마", 10751 to "가족", 14 to "판타지", 36 to "역사", 27 to "공포", 10402 to "음악", 9648 to "미스터리",
10749 to "로맨스", 878 to "SF", 10770 to "TV 영화", 53 to "스릴러", 10752 to "전쟁", 37 to "서부")


@Parcelize
data class Note( var title : String? = null, var imageUrl : String? = null, var contents : String? = null, var rating : Int = 0, var timestamp: Long? = null) :
    Parcelable {}
