package com.example.onlineshopping.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.R
import com.example.onlineshopping.adapters.CartAdapter
import com.example.onlineshopping.adapters.shoppingAdapter
import com.example.onlineshopping.model.cartData
import com.example.onlineshopping.model.shoppingdata
import com.google.firebase.database.*

class Cart : Fragment() {

    private lateinit var newCrtRecyclerView: RecyclerView
    private lateinit var tvCrtLoadingData : TextView
    private lateinit var newCrtArrayList: ArrayList<cartData>
    private lateinit var dbCrtRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)

        newCrtRecyclerView = view.findViewById(R.id.rvCart)
        newCrtRecyclerView.layoutManager = LinearLayoutManager(context)
        newCrtRecyclerView.setHasFixedSize(true)

        tvCrtLoadingData = view.findViewById(R.id.tvCrtLoadingData)

        newCrtArrayList = arrayListOf<cartData>()

        getCartData()

        return view
    }

    private fun getCartData() {
        newCrtRecyclerView.visibility = View.GONE
        tvCrtLoadingData.visibility = View.VISIBLE

        dbCrtRef = FirebaseDatabase.getInstance().getReference("CartItem")

        dbCrtRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newCrtArrayList.clear()

                if(snapshot.exists()){
                    for(crtSnap in snapshot.children){
                        val crtData = crtSnap.getValue(cartData::class.java)
                        newCrtArrayList.add(crtData!!)
                    }

                    val mAdapter = CartAdapter(newCrtArrayList)
                    newCrtRecyclerView.adapter = mAdapter

                    newCrtRecyclerView.visibility = View.VISIBLE
                    tvCrtLoadingData.visibility = View.GONE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}