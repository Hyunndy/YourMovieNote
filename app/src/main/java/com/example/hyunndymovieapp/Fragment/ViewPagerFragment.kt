package com.example.hyunndymovieapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.API_KEY
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.api.MovieManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



open class ViewPagerFragment : Fragment() {

    //@test
    private lateinit var movieListPager : ViewPager2
    private val movieManager by lazy { MovieManager() }
    protected var subscriptions = CompositeDisposable()
    private var job: Job? = null // (1) job변수선언
    var theMovieList : MovieList? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }

    override fun onResume() {
        super.onResume()
        job = null // (2) 재개될 때 코루틴 제거
    }

    override fun onPause() {
        super.onPause()
        job?.cancel() // (3) 코루틴의 취소 및 제거
        job = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestMovie(view)
    }

    private fun requestMovie(view: View){

        // (1) 코루틴의 launch 빌더
        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val param = mapOf(
                    "page" to (theMovieList?.page).toString(),
                    "api_key" to API_KEY,
                    "sort_by" to "popularity.desc",
                    "language" to "ko"
                )

                val retrivedMovie = movieManager.getMovieList(param)
                retrivedMovie.page = retrivedMovie.page?.plus(1)

                theMovieList = retrivedMovie
                showMovieListPage(view)
            } catch ( e: Throwable) {
                Log.d("TEST", "오류납니다.")
            }
        }
    }

    private fun showMovieListPage(view: View) {
        // 영화 목록 뷰페이저 Init
        Log.d("TEST", "쇼무비리스트페이지.")
        movieListPager = view.findViewById(R.id.movieListViewPager)
        // 뷰페이저 Adapter Init
        movieListPager.adapter  = MovieListAdapter(requireActivity(), 5, theMovieList)

        movieListPager.run { adapter?.notifyDataSetChanged() }
    }

}