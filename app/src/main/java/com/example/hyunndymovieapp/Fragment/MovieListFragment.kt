package com.example.hyunndymovieapp.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.example.hyunndymovieapp.MainActivity

import com.example.hyunndymovieapp.R
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import java.lang.ClassCastException

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.07
작성자: 유현지
클래스명: MovieListFragment
기능: 영화 포스터 목록


ViewModel로 처리할 수 없는 이벤트를 전달해야 할 경우 프래그먼트 안에서 콜백 인터페이스를 정의하고 호스트 액티비티가 이를 구현하도록 요구할 수 있습니다. 액티비티가 인터페이스를 통해 콜백을 수신하면, 필요에 따라 그 정보를 레이아웃 내의 다른 프래그먼트와 공유할 수 있습니다.
---------------------------------------------------------------------------------------------- */

class MovieListFragment : Fragment() {

    var mCallback : OnBtnSelectedListner? = null

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

        var view =
            LayoutInflater.from(activity).inflate(R.layout.fragment_movie_list, container, false)

        view.show_movie_detail_btn.setOnClickListener {
            mCallback?.onBtnSelected()
        }

        return view
    }
}

