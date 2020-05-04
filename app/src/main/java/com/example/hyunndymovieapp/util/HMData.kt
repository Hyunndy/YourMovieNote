package com.example.hyunndymovieapp.util



// Movie API 서비스를 위한 키
const val API_KEY = "b9e3a985b4c7aebdc3698d3f55d45d0e" // 부여받은 API 키

const val MOVIEAPI_URL = "https://api.themoviedb.org/3/"

const val SEARCH_URL = "https://www.justwatch.com/kr/검색?q="

enum class REQUEST(val value:Int){
    ADD_NOTE(100),
    LOGIN(200),
    LOGOUT(300),
    ADD_PHOTO(400)
}

enum class RESULT(val value:Int){
    SAVE_COMMENT(10),
    DELETE_COMMENT(20),
    SUCCESS_LOGIN(30),
    SUCCESS_LOGOUT(40),
    ADD_POSTER(50),
}

enum class CHANGECOMMENTLIST(val value:Int){
    ADD(1000),
    DELETE(2000),
    UPDATE(3000)
}

enum class USERSTATE(val value:Int) {
    LOGIN(1),
    LOGOUT(2)
}