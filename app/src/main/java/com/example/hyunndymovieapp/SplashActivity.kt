package com.example.hyunndymovieapp

import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import com.example.hyunndymovieapp.util.NetworkHelper

class SplashActivity : AppCompatActivity() {

    private val remainTime: Long = 1300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(NetworkHelper(applicationContext).checkNetworkState()) {
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, remainTime)
        }
    else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("네트워크 오류")
            builder.setMessage("네트워크를 연결해주세요")

            val listner = DialogInterface.OnClickListener { dialog, which ->
                when(which) {
                    BUTTON_POSITIVE -> finish()
                }
            }

            builder.setPositiveButton("확인", listner)
            builder.show()
        }
    }
}
