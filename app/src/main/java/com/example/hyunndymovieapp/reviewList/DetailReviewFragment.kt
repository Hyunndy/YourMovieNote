package com.example.hyunndymovieapp.reviewList


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.util.Note
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_review.*
import kotlinx.android.synthetic.main.fragment_detail_review.*

class DetailReviewFragment : Fragment() {

    private var viewModel = ReviewListViewModel()
    private var review : Note? = null
    private var reviewIdx : Int = 0
    private var posterUrl : Uri? = null

    val PICK_IMAGE_FROM_ALBUM = 0

    companion object {
        fun getInstance(Idx : Int) : Fragment {
            val fragment = DetailReviewFragment()
            fragment.reviewIdx = Idx
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[ReviewListViewModel::class.java]

        return inflater.inflate(R.layout.fragment_detail_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        review = getReview(viewModel)

        setInfoFromAPI()

        deleteBtn.setOnClickListener {
            FirebaseFirestore.getInstance().collection("MovieNote").document(review?.timestamp.toString()).delete().addOnSuccessListener {
                (activity as ReviewActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.movienote_frame, ReviewListFragment())
                    .commit()
            }
        }

        editBtn.setOnClickListener {
            saveBtn.visibility = VISIBLE
            note_title_d.isEnabled = true
            note_contents_d.isEnabled = true
            note_poster_d.isEnabled = true
        }

        saveBtn.setOnClickListener{
            saveBtn.isEnabled = false

            var map = mutableMapOf<String, Any>()
            map["title"] = note_title_d.text.toString()
            map["contents"] = note_contents_d.text.toString()
            map["rating"] = note_ratingBar_d.numStars
            map["imageUrl"] = posterUrl.toString()

            FirebaseFirestore.getInstance().collection("MovieNote").document(review?.timestamp.toString()).update(map).addOnCompleteListener {
                (activity as ReviewActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.movienote_frame, ReviewListFragment())
                    .commit()
            }
        }


        note_poster_d.setOnClickListener {
            openAlbum()
        }

    }

    var getReview : (ReviewListViewModel) -> Note? = { model : ReviewListViewModel->
        val reviewList = model.getReviewList()?.value
        reviewList?.get(reviewIdx) }

    private fun setInfoFromAPI() {
        note_title_d.setText(review?.title)
        note_contents_d.setText(review?.contents)
        note_ratingBar_d.rating = review?.rating?.toFloat() ?: 0.0F
        Glide.with(this).load(review?.imageUrl).into(note_poster_d)

        if (review?.imageUrl != null) {
            posterUrl = Uri.parse(review?.imageUrl)
        }
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
                Glide.with(this).load(posterUrl).into(note_poster_d)
            }
        }
    }
}
