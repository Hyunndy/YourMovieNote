package com.example.hyunndymovieapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import com.example.hyunndymovieapp.Fragment.MovieDetailFragment
import com.example.hyunndymovieapp.Fragment.MovieListFragment
import com.example.hyunndymovieapp.api.JSON_TYPE
import com.example.hyunndymovieapp.api.Movie
import com.example.hyunndymovieapp.api.MovieList
import com.example.hyunndymovieapp.api.VolleyService
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

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

var movieList = ArrayList<Movie>()
var movieFragmentList = ArrayList<MovieListFragment>()

class MainActivity : AppCompatActivity(), MovieListFragment.OnBtnSelectedListner, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var movieListPager : ViewPager
    private lateinit var movieListPagerAdapter : MovieListPagerAdapter
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar

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
                showMovieListPage()
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

        // 영화 목록 뷰페이저 Init
        movieListPager = findViewById(R.id.movie_list_pager)
        // 뷰페이저 Adapter Init
        movieListPagerAdapter = MovieListPagerAdapter(supportFragmentManager)

        // 영화 목록 API 받아오기
        VolleyService.loadMovieListInfo(this) { result ->
            if(result != null) {
                Log.d("test1", "createMovieListPager의 result${result.size}")
                movieList.clear()
                movieList.addAll(result)

                Log.d("test1", "createMovieListPager에서의 사이즈 ${movieList.size}")

                showMovieListPage()
            }
        }
    }

    private fun showMovieListPage() {
        movieListPagerAdapter.viewList.clear()

        // API에서 받아온 정보 세팅
        var idx = 0
        while(idx < movieList.size) {
            Log.d("test1", "${idx}")

            var newFragment = MovieListFragment()

            // 번들로 던진다.
            var bundle = Bundle()
            bundle.putParcelable("movieInfo", movieList[idx])
            newFragment.arguments = bundle

            movieListPagerAdapter.viewList.add(newFragment)

            idx++
        }

        movieListPagerAdapter.notifyDataSetChanged()
        // 어댑터
        movieListPager.adapter = movieListPagerAdapter
    }


    private fun showMovieDetailPage() {
        movieListPagerAdapter.viewList.clear()
        movieListPagerAdapter.addItem(MovieDetailFragment())

        movieListPager.adapter = movieListPagerAdapter
    }
    // MovieListFragment 를 위한 ViewPagerAdapter
    // ViewPager 내부를 차제하게 해주는 기본 클래스.
    @SuppressLint("WrongConstant")
    private inner class MovieListPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        var viewList = arrayListOf<Fragment>()

        fun addItem(newMovie : Fragment) {
            viewList.add(newMovie)
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return viewList.size
        }

        // Fragment제공
        override fun getItem(position: Int): Fragment{
            return viewList[position]
        }
    }

    override fun onBtnSelected() {
        showMovieDetailPage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}