package com.example.hyunndymovieapp.Fragment

import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.util.Property.of
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.api.MovieManager
import com.example.hyunndymovieapp.util.API_KEY
import com.example.hyunndymovieapp.util.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_blank2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.EnumSet.of
import java.util.Optional.of
import com.example.hyunndymovieapp.MainActivity as MainActivity



open class ViewPagerFragment : Fragment() {

    private lateinit var viewModel : MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[MovieListViewModel::class.java]

        Log.d("TEST3", "구독중..")
        viewModel.getMovieList()?.observe(viewLifecycleOwner, Observer { movie->
            Log.d("TEST3", "씨바랐@@@@@@@@@@@@@@")
            showMovieListPage(movie)
        })

        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMovieListPage(viewModel.getMovieList()?.value)
    }


    private fun showMovieListPage(movieList : MovieList?) {

        movieListViewPager.adapter  = MovieListAdapter(requireActivity(), 10, movieList)
        movieListViewPager.run { adapter?.notifyDataSetChanged() }
    }
}