package com.example.onlineshopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var nRecyclerView: RecyclerView
private lateinit var nArrayList: ArrayList<shoppingdata>

class Cart : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)

//        val imgView : ImageView = view.findViewById(R.id.ivCrt)
//        val args = this.arguments
//        val inputData = args?.get("shoppingitem")
//        imgView.setImageDrawable(inputData)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        nRecyclerView = view.findViewById(R.id.rvCart)
//        nRecyclerView.layoutManager = LinearLayoutManager(context)
//        nRecyclerView.setHasFixedSize(true)
//
//        nRecyclerView.adapter = shoppingAdapter()
    }
}