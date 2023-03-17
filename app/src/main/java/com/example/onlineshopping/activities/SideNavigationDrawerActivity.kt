package com.example.onlineshopping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.onlineshopping.fragments.AccountInfromation
import com.example.onlineshopping.fragments.Cart
import com.example.onlineshopping.fragments.Home
import com.example.onlineshopping.R
import com.example.onlineshopping.fragments.Insertion
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class SideNavigationDrawerActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_navigation_drawer)

        firebaseAuth = FirebaseAuth.getInstance()

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val headerView: View = navView.getHeaderView(0)
        val eMail: TextView = headerView.findViewById(R.id.user_Email)
        val eMailString = intent.getStringExtra("Email")
        eMail.text = eMailString.toString()

        replaceFragment(Home(), title = "Home")
        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_home -> replaceFragment(Home(),it.title.toString())
//                R.id.nav_accountInformation -> replaceFragment(AccountInfromation(),it.title.toString())
                R.id.nav_insertion -> replaceFragment(Insertion(),it.title.toString())
                R.id.nav_Cart -> replaceFragment(Cart(),it.title.toString())
                R.id.nav_signOut -> signOut()
            }

            true
        }



    }

    private fun signOut(){
        firebaseAuth.signOut()
        val intent = Intent(this, MainActivity()::class.java)
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