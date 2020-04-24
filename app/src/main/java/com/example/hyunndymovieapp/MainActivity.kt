package com.example.hyunndymovieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.hyunndymovieapp.Fragment.MovieDetailFragment
import com.example.hyunndymovieapp.Fragment.MovieListAdapter
import com.example.hyunndymovieapp.Fragment.MovieListFragment
import com.example.hyunndymovieapp.api.*
import com.google.android.material.navigation.NavigationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: MainActivity
---------------------------------------------------------------------------------------------- */


enum class REQUESTCODE(val value:Int){
    ADD_NEWCOMMENT(100),
}

enum class RESULTCODE(val value:Int){
    SAVE_NEWCOMMENT(10),
    DELETE_NEWCOMMENT(20)
}

enum class UPDATECOMMENTLISTCODE(val value:Int){
    ADD(1000),
    DELETE(2000),
    UPDATE(3000)
}

//@test2

private lateinit var toolbar : androidx.appcompat.widget.Toolbar



lateinit var movieItemList : List<MovieItem>
//var theMovieList : MovieList? = null
var movieFragmentList = ArrayList<MovieListFragment>()

class MainActivity : AppCompatActivity(), MovieListFragment.OnBtnSelectedListner, NavigationView.OnNavigationItemSelectedListener {

    //@test
    //lateinit var viewpager : ViewPager2
    private val movieManager by lazy {MovieManager()}
    protected var subscriptions = CompositeDisposable()
    protected var job: Job? = null // (1) job변수선언

    override fun onResume() {
        super.onResume()
        job = null // (2) 재개될 때 코루틴 제거
    }

    override fun onPause() {
        super.onPause()
        job?.cancel() // (3) 코루틴의 취소 및 제거
        job = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createMovieListPager()

        createNavigationBar()
    }


    private fun createNavigationBar() {

        nav_view.setNavigationItemSelectedListener(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // ActionBar 좌상단에 위치한 버튼
         var toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
         drawer_layout.addDrawerListener(toggle)
         toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_movie_list -> {
                //showMovieListPage()
            }
            R.id.nav_movie_API -> {
                Toast.makeText(applicationContext, "우왕우왕", Toast.LENGTH_LONG).show()
            }
            R.id.nav_movie_book -> {
                Toast.makeText(applicationContext, "우왕우왕", Toast.LENGTH_LONG).show()
            }
            R.id.nav_setting -> {
                Toast.makeText(applicationContext, "우왕우왕", Toast.LENGTH_LONG).show()
            }
        }


        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun createMovieListPager() {

        //requestMovie()
    }

   // private fun requestMovie(){
//
   //     // (1) 코루틴의 launch 빌더
   //     job = GlobalScope.launch(Dispatchers.Main) {
   //         try {
   //             val param = mapOf(
   //                 "page" to (theMovieList?.page).toString(),
   //                 "api_key" to API_KEY,
   //                 "sort_by" to "popularity.desc",
   //                 "language" to "ko"
   //             )
//
   //             val retrivedMovie = movieManager.getMovieList(param)
   //             retrivedMovie.page = retrivedMovie.page?.plus(1)
//
   //             theMovieList = retrivedMovie
   //             showMovieListPage()
   //         } catch ( e: Throwable) {
   //             Log.d("TEST", "오류납니다.")
   //         }
   //     }
   // }


   //private fun showMovieListPage() {
   //    // 영화 목록 뷰페이저 Init
   //    Log.d("TEST", "쇼무비리스트페이지.")
   //    viewpager = findViewById(R.id.movieListViewPager)
   //    // 뷰페이저 Adapter Init
   //    viewpager.adapter  = MovieListAdapter(this, 5, theMovieList)

   //    viewpager.run { adapter?.notifyDataSetChanged() }
   //}


        // viewpager는 냅두고.... 새로운 fragment로 대체하면안되나?
        /*
    //disable swiping
    mViewPager.beginFakeDrag();

    //enable swipi
    mViewPager.endFakeDrag();
     */
        private fun showMovieDetailPage() {

        //movieListPagerAdapter.viewList.clear()

            // 여기를 지금 bundle던지는 애로 바꿔야된다.
            //
        //movieListPagerAdapter.addItem(MovieDetailFragment())

        //movieListPager.adapter = movieListPagerAdapter
    }

    override fun onBtnSelected() {
        showMovieDetailPage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}