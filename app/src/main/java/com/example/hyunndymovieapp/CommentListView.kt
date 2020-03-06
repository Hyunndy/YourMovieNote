package com.example.hyunndymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyunndymovieapp.comment.CommentDTO
import com.example.hyunndymovieapp.comment.CommentRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_comment_list_view.*
import java.util.ArrayList

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: CommentListView
기능:
Main -> 모두보기 눌렀을 때 들어오는 Activity.
모든 아이템을 보여준다.
---------------------------------------------------------------------------------------------- */

class CommentListView : AppCompatActivity() {

    var commentListAdapter = CommentRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list_view)

        comment_list_layout.adapter = commentListAdapter
        comment_list_layout.layoutManager = LinearLayoutManager(this)

        getCommentList()
    }

    private fun getCommentList(){
        var commentList:ArrayList<CommentDTO>? = intent.getParcelableArrayListExtra("commentList")

        commentListAdapter.updateCommentList(commentList, UPDATECOMMENTLISTCODE.UPDATE.value)
    }
}
