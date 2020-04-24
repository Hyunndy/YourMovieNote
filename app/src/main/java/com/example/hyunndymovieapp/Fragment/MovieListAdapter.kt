package com.example.hyunndymovieapp.Fragment

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hyunndymovieapp.api.MovieList

// 1. activity : what hosts the adapter
// 2. the second is an Int that tells the adapter the number of items it’ll show.
class MovieListAdapter (activity: FragmentActivity, val itemsCount: Int, private var movieList : MovieList?) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment = MovieListFragment.getInstance(movieList?.results?.get(position), position)
}