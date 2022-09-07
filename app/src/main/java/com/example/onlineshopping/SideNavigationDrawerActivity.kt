package com.example.onlineshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class SideNavigationDrawerActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_navigation_drawer)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val user_Email: TextView = findViewById(R.id.user_Email)
//
//        var eml = intent.getStringExtra("Email").toString()
//        user_Email.text = eml

        navView.setNavigationItemSelectedListener {
//            val user_name: TextView = findViewById(R.id.user_name)
//            user_name.text = "Anmol"

            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> replaceFragment(Home(),it.title.toString())
                R.id.nav_accountInformation -> replaceFragment(AccountInfromation(),it.title.toString())
                R.id.nav_Cart -> replaceFragment(Cart(),it.title.toString())
                R.id.nav_signOut -> signOut()
            }

            true
        }



    }

    private fun signOut(){
//        var helper = MyDBHelper(applicationContext)
//        var db = helper.writableDatabase

        val intent = Intent(this,MainActivity()::class.java)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment,title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}