package com.example.usersactivtiy

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

       //레이아웃 변수들
        var logoutbutton = findViewById<Button>(R.id.logoutbutton)
        var addresstext = findViewById<TextView>(R.id.addresstext)
        var busanImg = findViewById<ImageView>(R.id.busanImg)
        var busanText = findViewById<TextView>(R.id.busanText)


        //주소값 받아넣기
        val result = intent.getStringExtra("result")
        addresstext.setText(result)

        //주소입력
        addresstext.setOnClickListener {

            intent = Intent(this, AddressActivity::class.java)
            startActivity(intent)

        }


       //버튼리스너들

        busanText.setOnClickListener {

            intent = Intent(this, BusanActivity::class.java)

            /*intent.putExtra("result",result)*/

            startActivity(intent)
        }



        busanImg.setOnClickListener {

            intent = Intent(this, BusanActivity::class.java)
          /*  intent.putExtra("result",result)*/
            startActivity(intent)
        }



        // 로그아웃
        logoutbutton.setOnClickListener {
            // 로그인 화면으로
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            Toast.makeText(this,"로그아웃 되었습니다",Toast.LENGTH_SHORT).show()
            auth?.signOut()

            val opt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val client = GoogleSignIn.getClient(this, opt)
            client.revokeAccess()
        }

    }

}
