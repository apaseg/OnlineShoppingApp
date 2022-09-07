package com.example.onlineshopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<shoppingdata>
//    private lateinit var newCartList: ArrayList<shoppingdata>

    lateinit var imageId: Array<Int>
    lateinit var title: Array<String>
    lateinit var about: Array<String>
    lateinit var rate: Array<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

//        newCartList = arrayListOf<shoppingdata>()
//
//        val ivCart: ImageView = view.findViewById(R.id.ivCart)
//
//        for(i in imageId.indices){
//            ivCart.setOnClickListener{
//                val shoppingData = shoppingdata(imageId[i],title[i],about[i],rate[i])
//                newCartList.add((shoppingData))
//
//                val bundle = Bundle()
//                bundle.putString("shoppingitem",imageId[i].toString())
//
//                val fragment = Cart()
//                fragment.arguments = bundle
//                fragmentManager?.beginTransaction()?.replace(R.id.flcart,fragment)?.commit()
//            }
//        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageId = arrayOf(
            R.drawable.terrain,
            R.drawable.adidas,
            R.drawable.boat,
            R.drawable.levis,
            R.drawable.milton
        )

        title = arrayOf(
            "Indian Terrain",
            "Adidas",
            "boat",
            "LEVI'S",
            "Milton"
        )

        about = arrayOf(
            "T-shirt for men",
            "shoes",
            "Ear buds",
            "T-shirt for women",
            "Water Bottle"
        )

        rate = arrayOf(
            "Rs. 599",
            "Rs. 1785",
            "Rs. 1999",
            "Rs. 999",
            "Rs. 395"
        )

        newRecyclerView = view.findViewById(R.id.rvHome)
        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<shoppingdata>()
        getUserdata()

    }

    private fun getUserdata(){
        for (i in imageId.indices){
            val shoppingData = shoppingdata(imageId[i],title[i],about[i],rate[i])
            newArrayList.add(shoppingData)
        }

        newRecyclerView.adapter = shoppingAdapter(newArrayList)
    }

}