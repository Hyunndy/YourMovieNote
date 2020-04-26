package com.example.hyunndymovieapp.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.*
import com.example.hyunndymovieapp.api.MovieItem

import com.example.hyunndymovieapp.comment.CommentDTO
import com.example.hyunndymovieapp.comment.CommentRecyclerViewAdapter
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

    // 좋아요/싫어요
    var likeCount = 0
    var dislikeCount = 0

    // 어뎁터
    var commentListAdapter = CommentRecyclerViewAdapter()

    companion object {
        //fun getInstance(movieInfo : MovieItem?, index : Int) : Fragment {
        fun getInstance(index : Int) : Fragment {
           //val args = Bundle()
           //args.putParcelable("movieInfo", movieInfo)

            val fragment = MovieDetailFragment()
            //fragment.arguments = args
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
        val movieList = viewModel.getMovieList()?.value
        movieInfo = movieList?.results?.get(selectedIdx)
        //movieInfo = arguments?.getParcelable<MovieItem>("movieInfo")
        setInfoFromAPI(movieInfo?.title, movieInfo?.release_date, movieInfo?.poster_path, movieInfo?.overview, movieInfo?.vote_average)

        setCommentlist()
        setBtnListener()
    }

    private fun setInfoFromAPI(title:String?, date:String?, url:String?, overview:String?, ratingNum : Float?) {

        Glide.with(this).asBitmap().load("https://image.tmdb.org/t/p/w500/${url}").into(detailPoster)

        // 타이틀
        detailTitle.text = title
        // 개봉일
        detailReleaseDate.text = date
        // 줄거리
        detailOverView.text = overview

        detailRatingbar.rating = ratingNum!!.div(2)
    }


    private fun setCommentlist(){
        comment_list_layout.adapter = commentListAdapter
        comment_list_layout.layoutManager = LinearLayoutManager(activity)

        // 줄 사이에 구분선 추가
        comment_list_layout.addItemDecoration(DividerItemDecoration(activity, 1))
    }

    private fun setBtnListener(){

        serachBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(SEARCH_URL+movieInfo?.title)
            )

            startActivity(intent)
        }

        // 작성하기
        new_comment_btn.setOnClickListener {
            Toast.makeText(activity, "작성하기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show()
            startActivityForResult(Intent(activity, NewCommentActivity::class.java), REQUEST.ADD_COMMENT.value)
        }

        // 모두보기
        show_all_comment_btn.setOnClickListener {
            Toast.makeText(activity, "모두보기 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show()
            //묶어서 보내기
            var intent = Intent(activity, CommentListView::class.java)
            intent.putParcelableArrayListExtra("commentList", commentListAdapter.commentList)
            startActivity(intent)
        }

        // 좋아요버튼
            movie_like_btn.setOnClickListener {
            if(movie_like_btn.isSelected) return@setOnClickListener

            if(movie_dislike_btn.isSelected) {
                movie_dislike_btn.isSelected = false
                dislikeCount--
                movie_dislike_count.text = dislikeCount.toString()
            }

            likeCount++
            movie_like_count.text = likeCount.toString()
            movie_like_btn.isSelected = true
        }

        // 싫어요버튼
            movie_dislike_btn.setOnClickListener{
            if(movie_dislike_btn.isSelected) return@setOnClickListener

            if(movie_like_btn.isSelected) {
                movie_like_btn.isSelected = false
                likeCount--
                movie_like_count.text = likeCount.toString()
            }

            dislikeCount++
            movie_dislike_count.text = dislikeCount.toString()
            movie_dislike_btn.isSelected = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == REQUEST.ADD_COMMENT.value && resultCode == RESULT.SAVE_COMMENT.value -> {
                val newMemoList : ArrayList<CommentDTO>? = arrayListOf()
                newMemoList?.add(data?.getParcelableExtra("newComment")!!)
                commentListAdapter.updateCommentList(newMemoList, CHANGECOMMENTLIST.ADD.value)
            }
        }
    }
}