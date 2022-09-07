package com.example.onlineshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val etEmail: EditText = findViewById(R.id.eEmail)
        val etPassword: EditText = findViewById(R.id.ePassword)
        val tvNewUser: TextView = findViewById(R.id.tvNewUser)

        btnLogin.setOnClickListener {
            var args = listOf<String>(etEmail.text.toString(),etPassword.text.toString()).toTypedArray()
            var cursor = db.rawQuery("SELECT * FROM USERS WHERE UEMAIL = ? AND PWD = ?",args)

            if(cursor.moveToNext()){
                val intent = Intent(this,SideNavigationDrawerActivity::class.java)
                intent.putExtra("Email",etEmail.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Please Register First!",Toast.LENGTH_SHORT).show()
            }

            etEmail.setText("")
            etPassword.setText("")

            etEmail.requestFocus()
        }


        tvNewUser.setOnClickListener{
            val intent =  Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}