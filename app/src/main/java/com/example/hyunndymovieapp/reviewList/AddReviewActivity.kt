package com.example.hyunndymovieapp.reviewList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.util.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_review.*
import java.text.SimpleDateFormat
import java.util.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: NewCommentActivity
기능:
Main -> 작성하기 눌렀을 때 들어오는 Activity.
새 메모를 작성한다.
---------------------------------------------------------------------------------------------- */

class AddReviewActivity : AppCompatActivity() {

    // 파이어베이스 인증 라이브러리.
    var auth : FirebaseAuth? = null
    // 파이어베이스 DB
    var firestore : FirebaseFirestore? = null
    // 데이터를 저장하는 버킷.
    var storage : FirebaseStorage? = null

    private var posterUrl : Uri? = null

    val PICK_IMAGE_FROM_ALBUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)

        initFirebase()

        // 저장 버튼
        note_saveBtn.setOnClickListener {
            note_saveBtn.isEnabled = false
            contentUpload()
        }

        note_poster.setOnClickListener { openAlbum() }

        if(intent.hasExtra("movietitle") && intent.hasExtra("movieposter")) {

            //제목
            note_title.setText(intent.getStringExtra("movietitle"))

            //포스터
            val tempUrl = intent.getStringExtra("movieposter")
            if(tempUrl != null) { posterUrl = Uri.parse(tempUrl) }
            Glide.with(this).load(tempUrl).into(note_poster)
        }
    }

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    private fun openAlbum(){
        // Intent에 type을 지정해서 갤러리를 열게 한다.
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"

        // 갤러리 Activity로 이동한다.
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // ADDPHOTO에 따라 이미지가 세팅된다.
        when {
            // 갤러리에서 사진을 선택했다면
            requestCode == PICK_IMAGE_FROM_ALBUM && resultCode == Activity.RESULT_OK -> {
                posterUrl = data?.data
                // ImageView에 사진을 띄운다.
                Glide.with(this).load(posterUrl).into(note_poster)
            }
        }
    }

    //{{ @HYEONJIY 선택된 사진을 store 버킷에 넣고, 성공한다면 contentDTO에 넣어서 Firebase DB에 넣어주는 함수. 제일 중요함
    private fun contentUpload(){

            val newNote = Note(
                note_title.text.toString(),
                posterUrl?.toString(),
                note_contents.text.toString(),
                note_ratingBar.numStars,
                System.currentTimeMillis()
            )

            firestore?.collection("MovieNote")?.document(newNote.timestamp.toString())?.set(newNote)
            finish()
    }
}
