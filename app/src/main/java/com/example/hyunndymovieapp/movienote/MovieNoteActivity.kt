package com.example.hyunndymovieapp.movienote


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.Fragment.ViewPagerFragment
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.Note
import com.example.hyunndymovieapp.util.MovieListViewModel
import com.example.hyunndymovieapp.util.REQUEST
import com.example.hyunndymovieapp.util.inflate
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comment_list_view.*
import kotlinx.android.synthetic.main.comment_detail.view.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: CommentListView
기능:
Main -> 모두보기 눌렀을 때 들어오는 Activity.
모든 아이템을 보여준다.
------------------------------------------------------------------------------------------------ */

class MovieNoteActivity : AppCompatActivity(), MovieNoteListFragment.OnNoteSelectedListner {

    private lateinit var model : ReviewListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_note)

        model = ViewModelProvider(this)[ReviewListViewModel::class.java]

        supportFragmentManager.beginTransaction()
            .replace(R.id.movienote_frame, MovieNoteListFragment())
            .commit()
    }

    //@TODO 받은 Note로 새 Fragment열기
    override fun onNoteSelected(selectedNoteIdx: Int) {
        Log.d("TEST33", "오잉??")
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.movienote_frame, DetailMovieNoteFragment.getInstance(selectedNoteIdx))
            .commit()
    }
}
