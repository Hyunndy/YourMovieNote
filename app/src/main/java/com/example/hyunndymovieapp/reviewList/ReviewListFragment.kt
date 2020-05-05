package com.example.hyunndymovieapp.reviewList

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.util.Note
import com.example.hyunndymovieapp.util.inflate
import kotlinx.android.synthetic.main.item_review.view.*
import kotlinx.android.synthetic.main.fragment_reviewlist.*

class ReviewListFragment : Fragment() {

    private lateinit var viewModel : ReviewListViewModel
    private lateinit var reviewListAdapter : MovieNoteRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[ReviewListViewModel::class.java]
        viewModel.getReviewList()?.observe(viewLifecycleOwner, Observer {
            if(reviewListAdapter.notes?.size != it.size) {
                reviewListAdapter.notes = it
                reviewListAdapter.notifyDataSetChanged()
            }
        })

        return inflater.inflate(R.layout.fragment_reviewlist, container, false)
    }

    //@TODO 인터페이스 생성 / 클릭하면 액티비티에서 상세노트로 Fragment 교체할 수 있도록.
    interface OnNoteSelectedListner {
        fun onNoteSelected(selectedNoteIdx : Int)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewListAdapter = MovieNoteRecyclerViewAdapter {
            (context as OnNoteSelectedListner).onNoteSelected(it)
        }

        comment_list_layout.adapter = reviewListAdapter

        comment_list_layout.layoutManager = LinearLayoutManager(activity)

        add_review_Btn.setOnClickListener { startActivity(Intent(activity,  AddReviewActivity::class.java)) }
    }


    inner class MovieNoteRecyclerViewAdapter(val clickedNote : (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

        var notes : ArrayList<Note>? = arrayListOf()

        private inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CustomViewHolder(parent.inflate(R.layout.item_review))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var view = holder.itemView
            Glide.with(this@ReviewListFragment).load(notes?.get(position)?.imageUrl).into(view.comment_user_profile)
            view.comment_user_id.text = notes?.get(position)?.title
            view.comment_detail_text.text = notes?.get(position)?.contents
            view.comment_user_ratingBar.rating = notes?.get(position)?.rating?.toFloat() ?: 0.0F

            view.setOnClickListener {
                clickedNote(position)
            }
        }

        override fun getItemCount(): Int {
            return notes?.size ?: 0
        }
    }
}
