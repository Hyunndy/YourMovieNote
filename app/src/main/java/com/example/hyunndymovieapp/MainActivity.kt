package com.example.hyunndymovieapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.hyunndymovieapp.Fragment.*
import com.example.hyunndymovieapp.util.MovieListViewModel
import com.example.hyunndymovieapp.util.REQUEST
import com.example.hyunndymovieapp.util.RESULT
import com.example.hyunndymovieapp.util.USERSTATE
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: MainActivity
---------------------------------------------------------------------------------------------- */

//@TODO 유저상태
var userState : USERSTATE = USERSTATE.LOGOUT
private lateinit var model : MovieListViewModel

class MainActivity : AppCompatActivity(), MovieListFragment.OnBtnSelectedListner, NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[MovieListViewModel::class.java]
        setContentView(R.layout.activity_main)
        createNavigationBar()

        //@TODO 로그인/로그아웃
        logInBtn.setOnClickListener {
            when(userState) {
                USERSTATE.LOGIN ->  startActivityForResult(Intent(this, LoginActivity::class.java), REQUEST.LOGOUT.value)
                USERSTATE.LOGOUT -> startActivityForResult(Intent(this, LoginActivity::class.java), REQUEST.LOGIN.value)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            // 로그인/로그아웃 누르고 로그인되서 나온 경우
             RESULT.SUCCESS_LOGIN.value-> {
                 userState = USERSTATE.LOGIN
                logInBtn.text = "로그아웃"
            }
            RESULT.SUCCESS_LOGOUT.value-> {
                userState = USERSTATE.LOGOUT
                logInBtn.text = "로그인"
            }
        }
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
            R.id.nav_movieList -> {
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
            R.id.nav_movieNote -> {
                Toast.makeText(applicationContext, "무비노트", Toast.LENGTH_LONG).show()
            }
            R.id.nav_favoriteMovie -> {
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
        //ft.replace(R.id.mainFragment, MovieDetailFragment.getInstance(theMovieList?.results?.get(listIdx)))
        ft.replace(R.id.mainFragment,MovieDetailFragment.getInstance(listIdx))
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}