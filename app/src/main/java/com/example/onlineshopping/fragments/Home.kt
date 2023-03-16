package com.example.onlineshopping.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.R
import com.example.onlineshopping.adapters.shoppingAdapter
import com.example.onlineshopping.model.cartData
import com.example.onlineshopping.model.shoppingdata
import com.google.firebase.database.*

class Home : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var newArrayList: ArrayList<shoppingdata>
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbCrtRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        newRecyclerView = view.findViewById(R.id.rvShoppingData)
        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        tvLoadingData = view.findViewById(R.id.tvLoadingData)

        newArrayList = arrayListOf<shoppingdata>()

        getEmployeesData()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbCrtRef = FirebaseDatabase.getInstance().getReference("CartItem")
    }

    private fun getEmployeesData() {
        newRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("shoppings")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newArrayList.clear()

                if(snapshot.exists()){
                    for(empSnap in snapshot.children){
                        val empData = empSnap.getValue(shoppingdata::class.java)
                        newArrayList.add(empData!!)
                    }

                    val mAdapter = shoppingAdapter(newArrayList)
                    newRecyclerView.adapter = mAdapter

                    newRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}