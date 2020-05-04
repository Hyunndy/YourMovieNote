package com.example.hyunndymovieapp.Fragment

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
import com.example.hyunndymovieapp.api.MovieItem

import com.example.hyunndymovieapp.util.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.detailPoster
import kotlinx.android.synthetic.main.fragment_movie_detail.detailReleaseDate
import kotlinx.android.synthetic.main.fragment_movie_detail.detailTitle

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.07
작성자: 유현지
클래스명: MovieDetailFragment
기능: 영화 상세화면
---------------------------------------------------------------------------------------------- */

class MovieDetailFragment : Fragment() {
    private lateinit var viewModel : MovieListViewModel

    private var movieInfo : MovieItem? = null
    private var selectedIdx : Int = 0


    companion object {
        fun getInstance(index : Int) : Fragment {

            val fragment = MovieDetailFragment()
            fragment.selectedIdx = index
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = container?.inflate(R.layout.fragment_movie_detail)

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

        Glide.with(this).asBitmap().load("https://image.tmdb.org/t/p/w500/${movieInfo?.poster_path}").into(detailPoster)

        // 타이틀
        detailTitle.text = movieInfo?.title
        // 개봉일
        detailReleaseDate.text =  movieInfo?.release_date
        // 줄거리
        detailOverView.text = movieInfo?.overview

        detailRatingbar.rating = movieInfo?.vote_average!!.div(2)
    }

    private fun setBtnListener(){

        serachBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(SEARCH_URL+movieInfo?.title)
            )

            startActivity(intent)
        }
    }
}