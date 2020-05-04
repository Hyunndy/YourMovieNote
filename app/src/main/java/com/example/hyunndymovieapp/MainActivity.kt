package com.example.hyunndymovieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.hyunndymovieapp.Fragment.*
import com.example.hyunndymovieapp.movienote.MovieNoteActivity
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

class MainActivity : AppCompatActivity(), MovieListFragment.OnBtnSelectedListner, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var model : MovieListViewModel
    private var permissionList = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[MovieListViewModel::class.java]
        setContentView(R.layout.activity_main)
        createNavigationBar()

        // 권한 요청
        ActivityCompat.requestPermissions(this, permissionList, 1)

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
                }
            }
            R.id.nav_movieNote -> {
                Toast.makeText(applicationContext, "무비노트", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MovieNoteActivity::class.java))
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
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        ft.replace(R.id.mainFragment,MovieDetailFragment.getInstance(listIdx))
        ft.commit()
    }

}