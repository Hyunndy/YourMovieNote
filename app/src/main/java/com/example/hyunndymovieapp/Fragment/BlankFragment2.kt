package com.example.hyunndymovieapp.Fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.api.MovieItem
import kotlinx.android.synthetic.main.fragment_blank2.*
import kotlinx.android.synthetic.main.fragment_movie_list.*

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment2 : Fragment() {

    companion object {
        fun getInstance(movieInfo : MovieItem?) : Fragment {

            // 여기서 movelist그대로 던지고
            val args = Bundle()
            args.putParcelable("movieInfo", movieInfo)

            val fragment = BlankFragment2()
            Log.d("TEST", "컴패니언오브젝트")
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieInfo = arguments?.getParcelable<MovieItem>("movieInfo")
        //var movieInfo = arguments?.getParcelable<Movie>("movieInfo")
        Log.d("TEST", "너왜안나와..?")
        //textview.text = movieInfo?.title
    }

}
