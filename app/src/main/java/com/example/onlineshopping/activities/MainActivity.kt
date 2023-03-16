package com.example.onlineshopping.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.onlineshopping.dabaseHelper.MyDBHelper
import com.example.onlineshopping.databinding.ActivityMainBinding
import com.example.onlineshopping.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var dbRegRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()

        binding.btnRegister.setOnClickListener {

            val etName = binding.etName.text.toString().trim()
            val etEmail = binding.etEmail.text.toString().trim()
            val etPassword = binding.etPassword.text.toString().trim()
            val etConfirmPassword = binding.etConfirmPassword.text.toString().trim()

            when {
                etName.isEmpty() -> {
                    binding.tilName.helperText = "Enter Name"
                }
                etEmail.isEmpty() -> {
                    binding.tilEmail.helperText = "Enter Email"
                    binding.tilName.helperText = null
                }
                etPassword.isEmpty() -> {
                    binding.tilPassword.helperText = "Enter Password"
                    binding.tilName.helperText = null
                    binding.tilEmail.helperText = validEmail()
                }
                etPassword != etConfirmPassword -> {
                    binding.tilConfirmPassword.helperText = "Password doesn't matches"
                    binding.tilName.helperText = null
                    binding.tilEmail.helperText = validEmail()
                    binding.tilPassword.helperText = null
                }
                etPassword == etConfirmPassword -> {
                    binding.tilName.helperText = null
                    binding.tilEmail.helperText = validEmail()
                    binding.tilPassword.helperText = null
                    binding.tilConfirmPassword.helperText = null
                }

            }

            var tilName = binding.tilName.helperText == null
            var tilEmail = binding.tilEmail.helperText == null
            var tilPassword = binding.tilPassword.helperText == null
            var tilConfirmPassword = binding.tilConfirmPassword.helperText == null

//            if(tilName && tilEmail && tilPassword && tilConfirmPassword && etName.isNotEmpty()){
//                var helper = MyDBHelper(applicationContext)
//                var db = helper.writableDatabase
//                var args = listOf<String>(binding.etEmail.text.toString()).toTypedArray()
//                var cursor = db.rawQuery("SELECT * FROM USERS WHERE UEMAIL = ?",args)
//
//                if(cursor.moveToNext()){
//                    Toast.makeText(this,"You have already registered with this Email Id",Toast.LENGTH_SHORT).show()
//                }
//
//                else {
//                    var cv = ContentValues()
//                    cv.put("UNAME", etName)
//                    cv.put("UEMAIL", etEmail)
//                    cv.put("PWD", etPassword)
//
//                    db.insert("USERS", null, cv)
//
//                    val intent = Intent(applicationContext, LoginActivity::class.java)
//                    startActivity(intent)
//
//                    binding.etName.setText("")
//                    binding.etEmail.setText("")
//                    binding.etPassword.setText("")
//                    binding.etConfirmPassword.setText("")
//
//                    binding.etName.requestFocus()
//                }
//
//            }

            if(tilName && tilEmail && tilPassword && tilConfirmPassword){
//
//                dbRegRef = FirebaseDatabase.getInstance().getReference("UserDetail")
//                val userId = dbRegRef.push().key!!
//
//                val user = UserData(userId,etName,etEmail,etPassword)
//
//                dbRegRef.child(userId).setValue(user)

                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.createUserWithEmailAndPassword(etEmail,etPassword)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this,"Registration Successful!",Toast.LENGTH_SHORT).show()
                            binding.etName.text.clear()
                            binding.etEmail.text.clear()
                            binding.etPassword.text.clear()
                            binding.etConfirmPassword.text.clear()
                            binding.etName.requestFocus()
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.tvAlreadyHaveAnAccount.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun emailFocusListener() {
        binding.etEmail.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.tilEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.etEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.etPassword.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.tilPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.etPassword.text.toString()
       if(passwordText.length < 8){
           return "Minimum 8 Character Password"
       }
        if(!passwordText.matches(".*[A-Z].*".toRegex())){
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex())){
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#$%^&+=].*".toRegex())){
            return "Must Contain 1 Special Character (@#$%^&+=)"
        }
        return null
    }
}