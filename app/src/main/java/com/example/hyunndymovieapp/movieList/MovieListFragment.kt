package com.example.hyunndymovieapp.movieList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.util.BITMAP_URL
import com.example.hyunndymovieapp.util.MovieGenre
import com.example.hyunndymovieapp.util.MovieItem
import kotlinx.android.synthetic.main.fragment_movielist.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.07
작성자: 유현지
클래스명: MovieListFragment
기능: 영화 포스터 목록

ViewModel로 처리할 수 없는 이벤트를 전달해야 할 경우 프래그먼트 안에서 콜백 인터페이스를 정의하고 호스트 액티비티가 이를 구현하도록 요구할 수 있습니다.
액티비티가 인터페이스를 통해 콜백을 수신하면, 필요에 따라 그 정보를 레이아웃 내의 다른 프래그먼트와 공유할 수 있습니다.
---------------------------------------------------------------------------------------------- */

class MovieListFragment : Fragment() {

    private var listIdx : Int = 0

    companion object {
        fun getInstance(movieInfo : MovieItem?, Idx : Int) : Fragment {

            val args = Bundle()
            args.putParcelable("movieInfo", movieInfo)

            val fragment = MovieListFragment()
            fragment.arguments = args
            fragment.listIdx = Idx

            return fragment
        }
    }

    interface OnBtnSelectedListner {
        fun onBtnSelected(listIdx : Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =  inflater.inflate(R.layout.fragment_movielist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try{
            val btnListner = context as OnBtnSelectedListner
            show_movie_detail_btn!!.setOnClickListener {
                btnListner.onBtnSelected(listIdx)
            }
        } catch (e : Exception) {

        }

        val movieInfo = arguments?.getParcelable<MovieItem>("movieInfo")
        setInfoFromAPI(movieInfo?.title, movieInfo?.release_date, movieInfo?.poster_path, movieInfo?.genre_ids)
    }

    private fun setInfoFromAPI(title:String?, date:String?, url:String?, genre : List<Int>?) {

            Glide.with(this).asBitmap().load(BITMAP_URL+url).into(detailPoster)

            // 타이틀
            detailTitle.text = title
            // 개봉일
            detailReleaseDate.text = date
            // 예매율
            //movie_reservation_value.text = reservation.toString()
            // 관람 가능 연령
            var tempgenre = ""
            if (genre != null) {
                for(id in genre) {
                    tempgenre += (MovieGenre[id] + " ")
                }
            }
            movie_age_rating.text = tempgenre
        }
    }


