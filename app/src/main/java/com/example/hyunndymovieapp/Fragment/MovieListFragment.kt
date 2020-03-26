package com.example.hyunndymovieapp.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.MainActivity

import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.Movie
import com.example.hyunndymovieapp.api.MovieList
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import java.lang.ClassCastException
import java.net.URL

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.07
작성자: 유현지
클래스명: MovieListFragment
기능: 영화 포스터 목록

ViewModel로 처리할 수 없는 이벤트를 전달해야 할 경우 프래그먼트 안에서 콜백 인터페이스를 정의하고 호스트 액티비티가 이를 구현하도록 요구할 수 있습니다.
액티비티가 인터페이스를 통해 콜백을 수신하면, 필요에 따라 그 정보를 레이아웃 내의 다른 프래그먼트와 공유할 수 있습니다.
---------------------------------------------------------------------------------------------- */

class MovieListFragment : Fragment() {

    var mCallback : OnBtnSelectedListner? = null
    var movieInfoList = ArrayList<Movie>()
    // 뷰
    private lateinit var fragmentView : View

    companion object{
        val MovieListFragment = MovieListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mCallback = context as? OnBtnSelectedListner
        if(mCallback == null) {
            throw ClassCastException("씨발")
        }
    }

    interface OnBtnSelectedListner {
        fun onBtnSelected()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("test1", "Fragment의 onCreateView")

        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_movie_list, container, false)

        fragmentView.show_movie_detail_btn.setOnClickListener {
            mCallback?.onBtnSelected()
        }

        var movieInfo = arguments?.getParcelable<Movie>("movieInfo")
        if(movieInfo != null) {
            setInfoFromAPI(movieInfo.image, movieInfo.title, movieInfo.date, movieInfo.reservation_rate, movieInfo.grade)
        }

        return fragmentView
    }



    //@HYEONJIY: 영화API 에서 불러온 이미지들을 세팅함.
    fun setInfoFromAPI(url:String?, title:String?, date:String?, reservation:Float?, grade:Int?) {

        Log.d("test1", "Fragment의 setInfoFromAPI")

        if(isAdded)
        {
            //포스터 URL
            Glide.with(this).asBitmap().load(url).into(fragmentView.movie_poster)

            // 타이틀
            fragmentView.movie_title.text = title
            // 개봉일
            fragmentView.movie_release_date.text = date
            // 예매율
            fragmentView.movie_reservation_value.text = reservation.toString()
            // 관람 가능 연령
            fragmentView.movie_age_rating.text = grade.toString()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {


        super.onActivityCreated(savedInstanceState)
    }
}

