package com.example.hyunndymovieapp.movieList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.hyunndymovieapp.LoginActivity
import com.example.hyunndymovieapp.R
import com.example.hyunndymovieapp.reviewList.ReviewActivity
import com.example.hyunndymovieapp.util.REQUEST
import com.example.hyunndymovieapp.util.RESULT
import com.example.hyunndymovieapp.util.USERSTATE
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.nav_user_email

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: MainActivity
---------------------------------------------------------------------------------------------- */

var userState : USERSTATE = USERSTATE.LOGOUT

class MainActivity : AppCompatActivity(), MovieListFragment.OnBtnSelectedListner, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var model : MovieListViewModel
    private var permissionList = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, permissionList, 1)
        createNavigationBar()

        logInBtn.setOnClickListener {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUEST.LOGOUT.value)
        }

        model = ViewModelProvider(this)[MovieListViewModel::class.java]
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT.SUCCESS_LOGIN.value -> {
                nav_view.nav_user_email.text = FirebaseAuth.getInstance().currentUser?.email
                logInBtn.setText(R.string.btn_logout)
            }
            RESULT.SUCCESS_LOGOUT.value-> {
                logInBtn.setText(R.string.btn_login)
                nav_view.nav_user_email.text = ""
            }
        }
    }

    private fun createNavigationBar() {

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{
            drawer_layout.openDrawer(GravityCompat.END)

            if(FirebaseAuth.getInstance().currentUser != null) {
                nav_user_email.text = FirebaseAuth.getInstance().currentUser!!.email
                logInBtn.setText(R.string.btn_logout)
            } else {
                logInBtn.setText(R.string.btn_login)
                nav_user_email.setText(R.string.nav_email)
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        nav_view.setNavigationItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_movieList -> {
                val fm = supportFragmentManager
                val temp = fm.findFragmentById(R.id.mainFragment)
                if(temp !is ViewPagerFragment) {
                    val ft = fm.beginTransaction()
                    ft.replace(
                        R.id.mainFragment,
                        ViewPagerFragment()
                    )
                    ft.commit()
                }
            }
            R.id.nav_movieNote -> {
                if(FirebaseAuth.getInstance().currentUser == null) {
                    Toast.makeText(applicationContext, "로그인 후 이용할 수 있습니다.", Toast.LENGTH_LONG).show()
                } else {
                    startActivity(Intent(this, ReviewActivity::class.java))
                }
            }
        }

        drawer_layout.closeDrawer(GravityCompat.END)

        return true
    }

    override fun onBtnSelected(listIdx : Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        ft.replace(
            R.id.mainFragment,
            DetailMovieFragment.getInstance(listIdx))
        ft.commit()
    }

}