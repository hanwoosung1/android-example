package com.example.usersactivtiy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

         var signup_okButton = findViewById<Button>(R.id.signup_okButton)
         var signupID = findViewById<EditText>(R.id.signupID)
         var signupPassword = findViewById<EditText>(R.id.signupPassword)
         var home = findViewById<Button>(R.id.home)

        home.setOnClickListener {
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }



        // 계정 생성 버튼
        signup_okButton.setOnClickListener {
            createAccount(signupID.text.toString(),signupPassword.text.toString())
        }
    }



    // 계정 생성
    private fun createAccount(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // 가입창 종료
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}