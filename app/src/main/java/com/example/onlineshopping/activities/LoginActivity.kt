package com.example.onlineshopping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.onlineshopping.dabaseHelper.MyDBHelper
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            val eEmail = binding.eEmail.text.toString().trim()
            val ePassword = binding.ePassword.text.toString().trim()

            if(eEmail.isNotEmpty() && ePassword.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(eEmail,ePassword)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(applicationContext, SideNavigationDrawerActivity::class.java)
                            intent.putExtra("Email",eEmail)
                            startActivity(intent)

                            Toast.makeText(this,"LogIn Successful!",Toast.LENGTH_SHORT).show()
                            binding.eEmail.text.clear()
                            binding.ePassword.text.clear()
                            binding.eEmail.requestFocus()
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this,"Empty Fields Are not Allowed!!",Toast.LENGTH_SHORT).show()
            }
        }


        binding.tvNewUser.setOnClickListener{
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}