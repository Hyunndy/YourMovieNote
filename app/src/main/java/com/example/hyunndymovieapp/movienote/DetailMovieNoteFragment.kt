package com.example.hyunndymovieapp.movienote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.Fragment.MovieDetailFragment

import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.MovieItem
import com.example.hyunndymovieapp.api.Note
import com.example.hyunndymovieapp.util.MovieListViewModel
import kotlinx.android.synthetic.main.activity_new_note.*
import kotlinx.android.synthetic.main.comment_detail.view.*
import kotlinx.android.synthetic.main.fragment_detail_movie_note.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class DetailMovieNoteFragment : Fragment() {

    private var viewModel = ReviewListViewModel()
    private var review : Note? = null
    private var reviewIdx : Int = 0

    companion object {
        fun getInstance(Idx : Int) : Fragment {
            val fragment = DetailMovieNoteFragment()
            fragment.reviewIdx = Idx
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[ReviewListViewModel::class.java]

        return inflater.inflate(R.layout.fragment_detail_movie_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        review = getReview(viewModel)

        setInfoFromAPI()
    }

    var getReview : (ReviewListViewModel) -> Note? = { model : ReviewListViewModel->
        val reviewList = model.getReviewList()?.value
        reviewList?.get(reviewIdx) }

    private fun setInfoFromAPI() {
        note_title_d.text = review?.title
        note_contents_d.text = review?.contents
        note_ratingBar_d.rating = review?.rating?.toFloat() ?: 0.0F
        Glide.with(this).load(review?.imageUrl).override(150,150).into(note_poster_d)
    }



}
