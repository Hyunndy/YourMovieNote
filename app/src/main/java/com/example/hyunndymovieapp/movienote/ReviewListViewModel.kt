package com.example.hyunndymovieapp.movienote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hyunndymovieapp.api.Note
import com.google.firebase.firestore.FirebaseFirestore

class ReviewListViewModel : ViewModel() {

    private var reviewList: MutableLiveData<ArrayList<Note>>? = MutableLiveData()
    private var selectedIdx: Int = 0

    init {
        loadReviewList()
    }

    fun getReviewList() : MutableLiveData<ArrayList<Note>>? = reviewList

    private fun loadReviewList() {

        // FireBase에 있는것 읽어오기
        FirebaseFirestore.getInstance().collection("MovieNote").orderBy("timestamp")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                Log.d("TEST33", "파이어베이스로드")

                if (querySnapshot == null) return@addSnapshotListener

                val tempList = ArrayList<Note>()

                for (snapshot in querySnapshot) {
                    tempList.add(snapshot.toObject(Note::class.java))
                }

                reviewList?.value?.clear()
                reviewList?.value = tempList
            }
    }
}