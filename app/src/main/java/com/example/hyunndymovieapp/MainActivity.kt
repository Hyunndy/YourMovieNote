package com.example.hyunndymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyunndymovieapp.comment.CommentRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 좋아요/싫어요
    var likeCount = 0
    var dislikeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCommentlist()

        setBtnListener()
    }

    private fun setCommentlist(){
        comment_list_layout.adapter = CommentRecyclerViewAdapter()
        comment_list_layout.layoutManager = LinearLayoutManager(this)

        // 줄 사이에 구분선 추가
        comment_list_layout.addItemDecoration(DividerItemDecoration(this, 1))
    }

    private fun setBtnListener(){

        // 작성하기
        new_comment_btn.setOnClickListener {
            Toast.makeText(this, "작성하기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show()
        }

        // 모두보기
        show_all_comment_btn.setOnClickListener {
            Toast.makeText(this, "모두보기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show()
        }

        // 좋아요버튼
        movie_like_btn.setOnClickListener {
            if(movie_like_btn.isSelected) return@setOnClickListener

            if(movie_dislike_btn.isSelected) {
                movie_dislike_btn.isSelected = false
                dislikeCount--
                movie_dislike_count.text = dislikeCount.toString()
            }

            likeCount++
            movie_like_count.text = likeCount.toString()
            movie_like_btn.isSelected = true
        }

        // 싫어요버튼
        movie_dislike_btn.setOnClickListener{
            if(movie_dislike_btn.isSelected) return@setOnClickListener

            if(movie_like_btn.isSelected) {
                movie_like_btn.isSelected = false
                likeCount--
                movie_like_count.text = likeCount.toString()
            }

            dislikeCount++
            movie_dislike_count.text = dislikeCount.toString()
            movie_dislike_btn.isSelected = true
        }
    }


}
