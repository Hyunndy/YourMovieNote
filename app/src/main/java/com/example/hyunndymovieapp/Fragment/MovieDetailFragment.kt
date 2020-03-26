package com.example.hyunndymovieapp.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyunndymovieapp.*

import com.example.hyunndymovieapp.comment.CommentDTO
import com.example.hyunndymovieapp.comment.CommentRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.07
작성자: 유현지
클래스명: MovieDetailFragment
기능: 영화 상세화면
---------------------------------------------------------------------------------------------- */

class MovieDetailFragment : Fragment() {

    // 좋아요/싫어요
    var likeCount = 0
    var dislikeCount = 0

    // 어뎁터
    var commentListAdapter = CommentRecyclerViewAdapter()

    // 뷰
    private lateinit var fragmentView : View

    companion object{
        val MovieDetailFragment = MovieDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_movie_detail, container, false)

        setCommentlist()
        setBtnListener()

        return fragmentView
    }


    private fun setCommentlist(){
        fragmentView.comment_list_layout.adapter = commentListAdapter
        fragmentView.comment_list_layout.layoutManager = LinearLayoutManager(activity)

        // 줄 사이에 구분선 추가
        fragmentView.comment_list_layout.addItemDecoration(DividerItemDecoration(activity, 1))
    }

    private fun setBtnListener(){

        // 작성하기
        fragmentView.new_comment_btn.setOnClickListener {
            Toast.makeText(activity, "작성하기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show()
            startActivityForResult(Intent(activity, NewCommentActivity::class.java), REQUESTCODE.ADD_NEWCOMMENT.value)
        }

        // 모두보기
        fragmentView.show_all_comment_btn.setOnClickListener {
            Toast.makeText(activity, "모두보기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show()
            //묶어서 보내기
            var intent = Intent(activity, CommentListView::class.java)
            intent.putParcelableArrayListExtra("commentList", commentListAdapter.commentList)
            startActivity(intent)
        }

        // 좋아요버튼
        fragmentView.movie_like_btn.setOnClickListener {
            if(fragmentView.movie_like_btn.isSelected) return@setOnClickListener

            if(fragmentView.movie_dislike_btn.isSelected) {
                fragmentView.movie_dislike_btn.isSelected = false
                dislikeCount--
                movie_dislike_count.text = dislikeCount.toString()
            }

            likeCount++
            fragmentView.movie_like_count.text = likeCount.toString()
            fragmentView.movie_like_btn.isSelected = true
        }

        // 싫어요버튼
        fragmentView.movie_dislike_btn.setOnClickListener{
            if(fragmentView.movie_dislike_btn.isSelected) return@setOnClickListener

            if(fragmentView.movie_like_btn.isSelected) {
                fragmentView.movie_like_btn.isSelected = false
                likeCount--
                fragmentView.movie_like_count.text = likeCount.toString()
            }

            dislikeCount++
            fragmentView.movie_dislike_count.text = dislikeCount.toString()
            fragmentView.movie_dislike_btn.isSelected = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUESTCODE.ADD_NEWCOMMENT.value -> {
                when(resultCode) {
                    RESULTCODE.SAVE_NEWCOMMENT.value -> {
                        var newMemoList : ArrayList<CommentDTO>? = arrayListOf()
                        newMemoList?.add(data?.getParcelableExtra("newComment")!!)

                        commentListAdapter.updateCommentList(newMemoList, UPDATECOMMENTLISTCODE.ADD.value)
                    }
                }
            }
        }
    }
}