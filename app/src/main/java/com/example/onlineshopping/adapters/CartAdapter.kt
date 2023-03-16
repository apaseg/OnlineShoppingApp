package com.example.onlineshopping.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.R
import com.example.onlineshopping.model.cartData
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.coroutines.coroutineContext

class CartAdapter(private val cartItemList: ArrayList<cartData>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val crntItem = cartItemList[position]

        holder.title.text = crntItem.tvCartTitle
        holder.about.text = crntItem.tvCartAbout
        holder.rate.text = crntItem.tvCartRate
        holder.homeImage.setImageURI(crntItem.itemCartImage?.toUri())
        holder.btnImage.setOnClickListener {
            Toast.makeText(holder.itemView.context,"Thank You for Buying this Item!",Toast.LENGTH_SHORT).show()
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return cartItemList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val homeImage: ImageView = itemView.findViewById(R.id.ivCrtHome)
        val title: TextView = itemView.findViewById(R.id.tvCrtTitle)
        val about: TextView = itemView.findViewById(R.id.tvCrtAbout)
        val rate: TextView = itemView.findViewById(R.id.tvCrtRate)
        val btnImage: Button = itemView.findViewById(R.id.btnCrt)
    }
}
