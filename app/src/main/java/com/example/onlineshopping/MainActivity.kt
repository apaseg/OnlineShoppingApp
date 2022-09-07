package com.example.onlineshopping

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.onlineshopping.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

            var tilName = binding.tilName.helperText == null
            var tilEmail = binding.tilEmail.helperText == null
            var tilPassword = binding.tilPassword.helperText == null
            var tilConfirmPassword = binding.tilConfirmPassword.helperText == null

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
                    binding.tilConfirmPassword.helperText == null
                }
            }

            if(tilName && tilEmail && tilPassword && tilConfirmPassword && etName.isNotEmpty()){
                var helper = MyDBHelper(applicationContext)
                var db = helper.writableDatabase
                var args = listOf<String>(binding.etEmail.text.toString()).toTypedArray()
                var cursor = db.rawQuery("SELECT * FROM USERS WHERE UEMAIL = ?",args)

                if(cursor.moveToNext()){
                    Toast.makeText(this,"You have already registered with this Email Id",Toast.LENGTH_SHORT).show()
                }

                else {
                    var cv = ContentValues()
                    cv.put("UNAME", etName)
                    cv.put("UEMAIL", etEmail)
                    cv.put("PWD", etPassword)

                    db.insert("USERS", null, cv)

                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)

                    binding.etName.setText("")
                    binding.etEmail.setText("")
                    binding.etPassword.setText("")
                    binding.etConfirmPassword.setText("")

                    binding.etName.requestFocus()
                }


//                val sharedPreference = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//                val editor = sharedPreference.edit()
//                editor.putString("name",binding.etName.text.toString())
//                editor.putString("email",binding.etEmail.text.toString())
//                editor.commit()
            }
        }

        binding.tvAlreadyHaveAnAccount.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
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