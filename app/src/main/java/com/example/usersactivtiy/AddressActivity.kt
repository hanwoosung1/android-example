package com.example.usersactivtiy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AddressActivity : AppCompatActivity() {
    private var et_address: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)


        et_address = findViewById<View>(R.id.et_address) as EditText
        val btn_search = findViewById<View>(R.id.button) as Button
        var Ok_button = findViewById<Button>(R.id.Ok_button)



    var  et_address_detail = findViewById<View>(R.id.et_address_detail) as EditText




        btn_search?.setOnClickListener {
            val i = Intent(this, KaKaoActivity::class.java)
            startActivityForResult(i, AddressActivity.Companion.SEARCH_ADDRESS_ACTIVITY)
        }





        Ok_button.setOnClickListener {


            var a = et_address!!.text.toString()
            var b = et_address_detail!!.text.toString()
            var result = (a + " " + b)

            if(result == null || result == " " ||result=="")
            {
                Toast.makeText(this,"주소를 입력하세요" ,Toast.LENGTH_SHORT)
                    .show()
            }
            else
            {
                intent = Intent(this, MainActivity::class.java)
                intent.putExtra("result",result)
                startActivity(intent)

            }



        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            AddressActivity.Companion.SEARCH_ADDRESS_ACTIVITY -> if (resultCode == RESULT_OK) {
                val data = intent!!.extras!!.getString("data")
                if (data != null) {
                    et_address!!.setText(data)
                }
            }
        }
    }


    companion object {
        private const val SEARCH_ADDRESS_ACTIVITY = 10000
    }
}