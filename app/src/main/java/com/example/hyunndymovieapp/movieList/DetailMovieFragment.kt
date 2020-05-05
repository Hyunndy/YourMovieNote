package com.example.hyunndymovieapp.movieList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.*
import com.example.hyunndymovieapp.reviewList.AddReviewActivity
import com.example.hyunndymovieapp.util.MovieItem

import com.example.hyunndymovieapp.util.*
import kotlinx.android.synthetic.main.fragment_detail_movie.*
import kotlinx.android.synthetic.main.fragment_movielist.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.07
작성자: 유현지
클래스명: MovieDetailFragment
기능: 영화 상세화면
---------------------------------------------------------------------------------------------- */

class DetailMovieFragment : Fragment() {

    private lateinit var viewModel : MovieListViewModel
    private var movieInfo : MovieItem? = null
    private var selectedIdx : Int = 0

    companion object {
        fun getInstance(index : Int) : Fragment {

            val fragment = DetailMovieFragment()
            fragment.selectedIdx = index
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = container?.inflate(R.layout.fragment_detail_movie)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MovieListViewModel::class.java]

        movieInfo = getMovie(viewModel)

        setting(::setInfoFromAPI, ::setBtnListener)
    }

    var getMovie : (MovieListViewModel) -> MovieItem? = { model : MovieListViewModel ->
        val movieList = model.getMovieList()?.value
        movieList?.results?.get(selectedIdx) }

    private fun setting( api : () -> Unit, btn : () -> Unit)  {
        api()
        btn()
    }

    private fun setInfoFromAPI() {

        // 타이틀
        detailTitle.text = movieInfo?.title
        //포스터
        Glide.with(this).load(BITMAP_URL+movieInfo?.poster_path).into(detailPoster)
        // 개봉일
        detailReleaseDate.text =  movieInfo?.release_date
        // 줄거리
        detailOverView.text = movieInfo?.overview
        // 별점
        detailRatingbar.rating = movieInfo?.vote_average ?: 0.0F
        // 장르
        var tempgenre = ""
        if (movieInfo?.genre_ids != null) {
            for (id in movieInfo?.genre_ids!!) {
                tempgenre += (MovieGenre[id] + " ")
            }
        }
        detailGenre.text = tempgenre
    }

    private fun setBtnListener(){

        serachBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(SEARCH_URL+movieInfo?.title)
            )

            startActivity(intent)
        }

         addreviewBtn.setOnClickListener {
             val movieInfoIntent = Intent(activity, AddReviewActivity::class.java)
             var tmep = BITMAP_URL+movieInfo?.poster_path
             movieInfoIntent.putExtra("movieposter", tmep)
             movieInfoIntent.putExtra("movietitle", movieInfo?.title)
             startActivity(movieInfoIntent)
         }
    }
}