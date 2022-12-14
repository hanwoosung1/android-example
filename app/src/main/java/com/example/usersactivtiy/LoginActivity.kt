package com.example.usersactivtiy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*




class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private var googleSignInClient : GoogleSignInClient? = null
    private var GOOGLE_LOGIN_CODE = 9001
    private var callbackManager : CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()


        var signupButton = findViewById<Button>(R.id.signupButton)

        var loginButton = findViewById<Button>(R.id.loginButton)

        var idEditText = findViewById<EditText>(R.id.idEditText)

        var passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        var facebookButton = findViewById<Button>(R.id.facebookButton)

        val googleButton = findViewById<Button>(R.id.googleButton)

        // ???????????? ?????????
        signupButton.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        // ????????? ??????
        loginButton.setOnClickListener {
            signIn(idEditText.text.toString(),passwordEditText.text.toString())
        }

        // ?????? ????????? ??????
        googleButton.setOnClickListener { googleLogin() }

        // ???????????? ????????? ??????
        facebookButton.setOnClickListener { facebookLogin() }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }



    // ?????????????????? ?????? ??? ?????? ????????? , ??????????????? ?????? ????????? ???
    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    // ?????????
    private fun signIn(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "???????????? ?????? ???????????????.",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText(
                            baseContext, "???????????? ?????? ???????????????.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    // ?????? ????????? ??????
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,GOOGLE_LOGIN_CODE)
    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    // ?????????, ???????????? ?????? ???
                    moveMainPage(task.result?.user)
                }else{
                    // ????????? ???
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

    // ???????????? ????????? ??????
    fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile","email"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    // ????????? ?????????
                    handleFacebookAccessToken(result?.accessToken)
                    // ????????????????????? ????????? ???????????? ?????????
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }
            })
    }

    fun handleFacebookAccessToken(token : AccessToken?){
        var credential = FacebookAuthProvider.getCredential(token?.token!!)

        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    // ?????????, ???????????? ?????? ???
                    moveMainPage(task.result?.user)
                    Toast.makeText(this,"????????? ??????",Toast.LENGTH_SHORT).show()
                }else{
                    // ????????? ???
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode,resultCode,data)

        if(requestCode == GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!
            // ??????API??? ???????????? ??? ?????????

            if(result.isSuccess) {
                var accout = result.signInAccount
                firebaseAuthWithGoogle(accout)
                Toast.makeText(this,"????????? ??????",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"????????? ??????",Toast.LENGTH_SHORT).show()
            }
        }
    }



    // ???????????? ???????????? ?????? ???????????? ??????
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}
