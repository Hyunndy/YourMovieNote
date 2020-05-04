package com.example.hyunndymovieapp.reviewList


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hyunndymovieapp.R

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: CommentListView
기능:
Main -> 모두보기 눌렀을 때 들어오는 Activity.
모든 아이템을 보여준다.
------------------------------------------------------------------------------------------------ */

class ReviewActivity : AppCompatActivity(), ReviewListFragment.OnNoteSelectedListner {

    private lateinit var model : ReviewListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_main)

        model = ViewModelProvider(this)[ReviewListViewModel::class.java]

        supportFragmentManager.beginTransaction()
            .replace(R.id.movienote_frame, ReviewListFragment())
            .commit()
    }

    //@TODO 받은 Note로 새 Fragment열기
    override fun onNoteSelected(selectedNoteIdx: Int) {
        Log.d("TEST33", "오잉??")
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.movienote_frame, DetailReviewFragment.getInstance(selectedNoteIdx))
            .commit()
    }
}
