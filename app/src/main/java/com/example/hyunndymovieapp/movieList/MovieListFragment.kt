package com.example.hyunndymovieapp.movieList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.util.BITMAP_URL
import com.example.hyunndymovieapp.util.MovieGenre
import com.example.hyunndymovieapp.util.MovieItem
import kotlinx.android.synthetic.main.fragment_movielist.*

/* ----------------------------------------------------------------------------------------------
클래스명: MovieListFragment
내용:
ViewPager안에 들어가는 개별 Fragment.
TMDB API에서 받아온 영화를 표시.
---------------------------------------------------------------------------------------------- */

class MovieListFragment : Fragment() {

    private var listIdx: Int = 0
    private lateinit var viewModel: MovieListViewModel

    companion object {
        fun getInstance(Idx: Int): Fragment {

            val fragment = MovieListFragment()
            fragment.listIdx = Idx

            return fragment
        }
    }

    interface OnBtnSelectedListner {
        fun onBtnSelected(listIdx: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movielist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MovieListViewModel::class.java]

        try {
            val btnListner = context as OnBtnSelectedListner
            show_movie_detail_btn!!.setOnClickListener {
                btnListner.onBtnSelected(listIdx)
            }
        } catch (e: Exception) {

        }

        setInfo((viewModel.getMovieList()?.value?.results?.get(listIdx)))
    }

    var setInfo: (MovieItem?) -> Unit = { movieItem ->

        // 타이틀
        movielist_title.text = movieItem?.title
        // 포스터
        Glide.with(this).asBitmap().load(BITMAP_URL + movieItem?.poster_path).into(movielist_poster)

        // 장르
        var tempgenre = ""
        if (movieItem?.genre_ids != null) {
            for (id in movieItem.genre_ids) {
                tempgenre += (MovieGenre[id] + " ")
            }
        }
        movielist_genre.text = tempgenre

        //별점
        movielist_ratingbar.rating = movieItem?.vote_average ?: 0.0F
    }
}


