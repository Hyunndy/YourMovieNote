package com.example.hyunndymovieapp.comment

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.util.CHANGECOMMENTLIST
import com.example.hyunndymovieapp.util.TimeHelper
import com.example.hyunndymovieapp.util.inflate
import kotlinx.android.synthetic.main.comment_detail.view.*
import org.w3c.dom.Comment

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: CommentRecyclerViewAdapter
기능: 한줄평 RecyclerView
---------------------------------------------------------------------------------------------- */

class CommentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var commentList : ArrayList<CommentDTO> = arrayListOf()

/*    init {
        var snapshot = CommentDTO()

        snapshot.userId = "guswlapdlzj"
        snapshot.userProfile = R.drawable.user1
        snapshot.comment = "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요."
        snapshot.ratingFigure = 3.0F
        snapshot.recommendedcount = 3
        snapshot.registeredTime = System.currentTimeMillis()

        commentList.add(snapshot)
    }*/

    fun updateCommentList(newCommentList : ArrayList<CommentDTO>?, updateCode : Int) {

        when(updateCode) {
            CHANGECOMMENTLIST.ADD.value -> {
                commentList.add(newCommentList!![0])
            }
            CHANGECOMMENTLIST.UPDATE.value -> {
                commentList.clear()
                commentList = newCommentList!!
            }
        }
        notifyDataSetChanged()
    }



    inner class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CustomViewHolder(parent.inflate(R.layout.comment_detail))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var viewHolder = (holder as CustomViewHolder).itemView
        viewHolder.comment_user_profile.setImageResource(commentList[position].userProfile!!)
        viewHolder.comment_user_id.text = commentList[position].userId
        viewHolder.comment_registered_time.text = TimeHelper().formatTimetoString(commentList[position].registeredTime!!)
        viewHolder.comment_detail_text.text = commentList[position].comment
        viewHolder.comment_recommended_count.text = commentList[position].recommendedcount.toString()
        viewHolder.comment_user_ratingBar.rating = commentList[position].ratingFigure!!
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}