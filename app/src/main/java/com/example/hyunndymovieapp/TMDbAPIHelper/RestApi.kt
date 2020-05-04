package com.example.hyunndymovieapp.TMDbAPIHelper

import com.example.hyunndymovieapp.util.MOVIEAPI_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/*
RESTAPI 초기화에를 위해 Retrofit의 빌더 사용
 */

class RestApi {
    private val theMovieService : TheMovieService

    // 1. Retrofit의 Builder를 통해 URL을 초기화하고 JSON를 위한 컨버터를 Moshi로 지정한다.
    // 2. retrofit.create()의 TheMovieService::class.java와 같은 형식은 자바 클래스의 인스턴스를 코틀린의 KClass 인스턴스로 사용할 수 있게 해준다.
    // 3. 이렇게 해서 최종적으로 요청을 보내고 영화 정보를 가져오는 getMovieListRetrofit()함수가 구성되었다.
    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(MOVIEAPI_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // (1) 코루틴을 사용하는 경우
            .build()

        theMovieService = retrofit.create(TheMovieService::class.java)
    }

    suspend fun getMovieListCo(param: Map<String, String>): MovieListResponse { // (2)
        return theMovieService.getDeferredTop(param).await()
    }

}