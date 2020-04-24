package com.example.hyunndymovieapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.api.MovieManager
import com.example.hyunndymovieapp.util.API_KEY
import kotlinx.android.synthetic.main.fragment_blank2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

var theMovieList : MovieList? = null

open class ViewPagerFragment : Fragment() {

    //@test
    private lateinit var movieListPager : ViewPager2
    private val movieManager by lazy { MovieManager() }
    private var job: Job? = null // (1) job변수선언


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }

    override fun onResume() {
        super.onResume()
        job = null // (2) 재개될 때 코루틴 제거
        Log.d("TEST2", "재개.")
    }

    override fun onPause() {
        super.onPause()
        job?.cancel() // (3) 코루틴의 취소 및 제거
        job = null
        Log.d("TEST2", "퍼즈.")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMovie(view)
    }

    private fun requestMovie(view: View){

        val now = LocalDate.now()
        val nowstr = now.format(DateTimeFormatter.ISO_DATE)
        val prevMonth = now.minusMonths(1)
        val prevstr = prevMonth.format(DateTimeFormatter.ISO_DATE)
        // (1) 코루틴의 launch 빌더

        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val param = mapOf(
                    "page" to "1",
                    "api_key" to API_KEY,
                    //"primary_release_date.gte" to prevstr,
                    //"primary_release_date.lte" to nowstr,
                    "language" to "ko",
                    "sort_by" to "popularity.desc"
                )

                val retrivedMovie = movieManager.getMovieList(param)
                retrivedMovie.page = retrivedMovie.page?.plus(1)

                theMovieList = retrivedMovie
                showMovieListPage(view)
            } catch ( e: Exception) {
                e.printStackTrace()
                Log.d("TEST2", "오류납니다.")
            }
        }
    }

    private fun showMovieListPage(view: View) {

        movieListViewPager.adapter  = MovieListAdapter(requireActivity(), 10, theMovieList)

        movieListViewPager.run { adapter?.notifyDataSetChanged() }
    }
}