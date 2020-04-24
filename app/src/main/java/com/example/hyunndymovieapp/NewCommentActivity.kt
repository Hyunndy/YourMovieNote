package com.example.hyunndymovieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hyunndymovieapp.comment.CommentDTO
import com.example.hyunndymovieapp.util.RESULTED
import kotlinx.android.synthetic.main.activity_new_comment.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: NewCommentActivity
기능:
Main -> 작성하기 눌렀을 때 들어오는 Activity.
새 메모를 작성한다.
---------------------------------------------------------------------------------------------- */

class NewCommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_comment)

        // 저장 버튼
        new_comment_save_btn.setOnClickListener {

            // 데이터 묶기
            var newComment = CommentDTO()
            newComment.userId = "yoosa2004"
            newComment.userProfile = R.drawable.user1
            newComment.comment = new_comment.text.toString()
            newComment.ratingFigure = movie_ratingBar.rating
            newComment.registeredTime = System.currentTimeMillis()
            newComment.recommendedcount = 0

            var intent = Intent()
            intent.putExtra("newComment", newComment)
            setResult(RESULTED.SAVE_COMMENT.value, intent)
            Toast.makeText(applicationContext, "메모가 저장되었습니다.", Toast.LENGTH_LONG).show()
            finish()
        }

        // 취소
        new_comment_delete_btn.setOnClickListener {

            // 안보내기
            setResult(RESULTED.DELETE_COMMENT.value)
            Toast.makeText(applicationContext, "메모가 저장이 취소되고 그냥 돌아갑니다.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
