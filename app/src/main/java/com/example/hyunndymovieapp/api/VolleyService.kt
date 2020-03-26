package com.example.hyunndymovieapp.api

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hyunndymovieapp.Fragment.MovieListFragment
import com.example.hyunndymovieapp.MainActivity
import com.google.gson.Gson

enum class JSON_TYPE(var value:Int) {
    MOVIELIST(100),
    MOVIEDETAILINFO(200),
    COMMENT(300)
}

// 싱글톤 파일 생성
object VolleyService {

    private lateinit var targetUrl:String
    private lateinit var newRequest:RequestQueue

    fun loadMovieListInfo(context: Context, list:(ArrayList<Movie>?) -> Unit) {

        targetUrl = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList?type=1"

        val movieListRequest = object : StringRequest(Request.Method.GET,
            targetUrl,
            Response.Listener { response ->
                Log.d("test1", response)
                var movieList = Gson().fromJson(response, MovieList::class.java)
                if(movieList != null) {
                    list(movieList.result)
                }
            },
            Response.ErrorListener { error ->
                list(arrayListOf())
            }) {}


        // Volley는 내부 캐싱기능이 있어 한 번 보내고 받은 응답결과가 있으면 그 다음에 보냈을 때 이전게있으면 이전걸 보여줄 수 있다.
        // 내부 캐싱을 false로 만들어서 이전 결과가 있어도 새로 요청한 응답을 보여주게 한다.
        movieListRequest.setShouldCache(false)
        newRequest = Volley.newRequestQueue(context)
        newRequest.add(movieListRequest)
    }

}