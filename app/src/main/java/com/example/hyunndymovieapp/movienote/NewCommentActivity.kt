package com.example.hyunndymovieapp.movienote

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_note.*
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

class NewCommentActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_new_note)

        initFirebase()

        // 저장 버튼
        note_saveBtn.setOnClickListener { contentUpload() }
        note_poster.setOnClickListener { openAlbum() }
    }

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    private fun openAlbum(){
        // Intent에 type을 지정해서 갤러리를 열게 한다.
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
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


    // @TODO 이걸 NEWCOMMENT에서 해야것는디
    //{{ @HYEONJIY 선택된 사진을 store 버킷에 넣고, 성공한다면 contentDTO에 넣어서 Firebase DB에 넣어주는 함수. 제일 중요함
    @SuppressLint("SimpleDateFormat")
    private fun contentUpload(){

        if(posterUrl == null) {
            var newNote = Note(note_title.text.toString(),  null, note_contents.text.toString(), note_ratingBar.numStars, System.currentTimeMillis())
            firestore?.collection("MovieNote")?.document(note_title.text.toString())?.set(newNote)
            setResult(Activity.RESULT_OK)
            finish()

        } else {

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            val imageFilename = "IMAGE" + timestamp + "_.png"

            // Firebasestorage 참조 변수를 선언해서, 파일이름을 포함하여 파일의 전체 경로를 가리키는 참조를 생성한다.
            val storageRef = storage?.reference?.child("images")?.child(imageFilename)

            // putfile() = 카메라의 사진, 동영상과 같은 기기의 로컬 파일을 업로드 한다.
            // File을 취하고 UploadTask를 반환하며 이 반환 객체를 사용하여 업로드를 관리하고 상태를 모니터링할 수 있다.
            val uploadTask = storageRef?.putFile(posterUrl!!)

            // 업로드가 성공적이면 Task를 통해 다운로드 URL 가져오기
            var urlTask = uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef?.downloadUrl
            }?.addOnSuccessListener {

                // 이미지의 url을 가져온다.
                storageRef?.downloadUrl?.addOnSuccessListener { uri ->

                    var newNote = Note(
                        note_title.text.toString(),
                        uri.toString(),
                        note_contents.text.toString(),
                        note_ratingBar.numStars,
                        System.currentTimeMillis()
                    )

                    firestore?.collection("MovieNote")?.document(note_title.text.toString())
                        ?.set(newNote)

                    setResult(Activity.RESULT_OK)

                    finish()
                }
            }
        }
    }
}
