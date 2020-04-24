package com.example.hyunndymovieapp.api

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/*
REST 요청을 위한 인터페이스와 빌더
 */

/*
Retrofit의 @GET 어노테이션으로 HTTP의 GET 요청으로 JSON을 읽어온다.
 */
interface TheMovieService {
    @GET("discover/movie")
    /**
     * REST 요청을 처리하기 위한 메서드
     * @param par QueryMap을 통해 질의한 쿼리문을 Map으로부터 받는다.
     * @return Call<T> 콜백 인터페이스 반환, T는 주고 받을 데이터 구조
     * @QueryMap 어노테이션은 위치가 바뀌어도 동적으로 값을 받아올 수 있게 한다.
     */
    fun getTop(@QueryMap par: Map<String, String>): Call<MovieListResponse>

    // Retrofit2에서 사용하는 @GET 어노테이션을 통해 BASE_URL과 함께 사용되는 URL이 지정된다.
    // page와 같이 보낼 변수들은 @QueryMap 어노테이션에 의해 Map<>정보로 사용될것이다.
    // 필요한 경우 @Headers 어노테이션으로 받을 형식을 지정할 수 있다.
    // @Headers({"Accept: application/json"})
    // 반환자료형은 Call<MovieListResponse>인데 이 Call 클래스는 Retrofit이 제공하는 클래스로 각 요청 처리가 성공인지 실패인지에 따라 실행하도록 구성할 수 있다.
    // 또, 제네릭 자료형으로 선언된 MovieListResponse는 서버로부터 응답 받은 데이터를 저장할 클래스가 된다.

    /**
     * 코틀린을 사용하는 버전의 요청 메서드 추가 부분
     * @return Deferred<T> 코루틴의 지연된 콜백 인터페이스 반환, T는 주고 받을 데이터 구조
     */
    @GET("discover/movie")
    fun getDeferredTop(@QueryMap par: Map<String, String>): Deferred<MovieListResponse>

}