package com.example.hyunndymovieapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hyunndymovieapp.movieList.MainActivity
import com.example.hyunndymovieapp.util.RESULT
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    // Firebase Authentication 관리 클래스
    private var auth: FirebaseAuth? = null

    // GoogleLogin 관리 클래스
    private var googleSignInClient: GoogleSignInClient? = null

    //GoogleLogin
    private val GOOGLE_LOGIN_CODE = 9001 // Intent Request ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase 로그인 통합 관리하는 Object 만들기
        auth = FirebaseAuth.getInstance()
        if(auth?.currentUser?.email!=null){
            Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
            auth?.signOut()
            setResult(RESULT.SUCCESS_LOGOUT.value)
            finish()
        }

        //구글 로그인 옵션
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //구글 로그인 클래스를 만듬
        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when {
            // 구글에서 승인된 정보를 가지고 오기
            requestCode == GOOGLE_LOGIN_CODE && resultCode == Activity.RESULT_OK -> {

                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result?.isSuccess!!) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account!!)
                }
            }
        }
    }


    private fun createAndLoginEmail() {
        auth?.createUserWithEmailAndPassword(username.text.toString(), password.text.toString())?.addOnCompleteListener {
            if(it.isSuccessful) {
                //로그인 성공 및 다음페이지 호출
                moveMainPage(auth?.currentUser)
            } else if(it.exception?.message.isNullOrEmpty()){
                Toast.makeText(applicationContext, it.exception!!.message, Toast.LENGTH_SHORT).show()
            } else {
                signinEmail()
            }
        }
    }

    private fun signinEmail() {
        auth?.signInWithEmailAndPassword(username.text.toString(), password.text.toString())?.addOnCompleteListener {
            if(it.isSuccessful) {
                //로그인 성공 및 다음페이지 호출
                moveMainPage(auth?.currentUser)
            }else {
                Toast.makeText(applicationContext, "잘못된 이메일 또는 비밀번호 입니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun moveMainPage(user: FirebaseUser?) {

        // @TODO 메인페이지에서 로그인유저 닉네임 알 수 있게 여기서 던지자.
        if (user != null) {
            setResult(RESULT.SUCCESS_LOGIN.value)
            finish()
        }
    }

    override fun onBackPressed() {
        setResult(RESULT.SUCCESS_LOGOUT.value)
        super.onBackPressed()
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //다음페이지 호출
                    moveMainPage(auth?.currentUser)
                }
            }
    }

    fun googleLogin(view: View) {
        Toast.makeText(this, "구글로그인", Toast.LENGTH_SHORT).show()
        val signInIntent = googleSignInClient?.signInIntent

        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }
    fun emailLogin(view: View) {
        Toast.makeText(this, "이메일로그인", Toast.LENGTH_SHORT).show()
        if (username.text.toString().isEmpty() || password.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show()

        } else {
            createAndLoginEmail()
        }
    }
}
