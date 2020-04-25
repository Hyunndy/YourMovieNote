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
import com.example.hyunndymovieapp.Fragment.*
import com.example.hyunndymovieapp.api.*
import com.example.hyunndymovieapp.util.NetworkHelper
import com.google.android.material.navigation.NavigationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_blank2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: MainActivity
---------------------------------------------------------------------------------------------- */

class MainActivity : AppCompatActivity(), MovieListFragment.OnBtnSelectedListner, NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        createNavigationBar()
    }


    private fun createNavigationBar() {

        nav_view.setNavigationItemSelectedListener(this)

        setSupportActionBar(toolbar)

        // ActionBar 좌상단에 위치한 버튼
         val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
         drawer_layout.addDrawerListener(toggle)
         toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_movie_list -> {
                val fm = supportFragmentManager
                val temp = fm.findFragmentById(R.id.mainFragment)
                if(temp !is ViewPagerFragment) {
                    val ft = fm.beginTransaction()
                    ft.replace(R.id.mainFragment, ViewPagerFragment())
                    ft.commit()
                    Toast.makeText(applicationContext, "현재 뷰페이저아님", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "현재 뷰페이저임", Toast.LENGTH_LONG).show()
                }
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

    override fun onBtnSelected(listIdx : Int) {
        Toast.makeText(applicationContext, "최종완성", Toast.LENGTH_LONG).show()

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.addToBackStack(null)
        ft.replace(R.id.mainFragment, MovieDetailFragment.getInstance(theMovieList?.results?.get(listIdx)))
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}