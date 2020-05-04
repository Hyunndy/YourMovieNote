package com.example.hyunndymovieapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.util.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_blank2.*

open class ViewPagerFragment : Fragment() {

    private lateinit var viewModel : MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[MovieListViewModel::class.java]

        viewModel.getMovieList()?.observe(viewLifecycleOwner, Observer { movie->
            showMovieListPage(movie)
        })

        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMovieListPage(viewModel.getMovieList()?.value)
    }


    private fun showMovieListPage(movieList : MovieList?) {

        movieListViewPager.adapter  = MovieListAdapter(requireActivity(), movieList?.results?.size ?: 10 , movieList)
        movieListViewPager.run { adapter?.notifyDataSetChanged() }
    }
}