package com.example.onlineshopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class shoppingAdapter(private val shoppingItemList: ArrayList<shoppingdata>) : RecyclerView.Adapter<shoppingAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): shoppingAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: shoppingAdapter.MyViewHolder, position: Int) {
        val currentItem = shoppingItemList[position]
        holder.homeImage.setImageResource(currentItem.ivHome)
        holder.title.text = currentItem.tvTitle
        holder.about.text = currentItem.tvAbout
        holder.rate.text = currentItem.tvRate
    }

    override fun getItemCount(): Int {

        return shoppingItemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val homeImage: ImageView = itemView.findViewById(R.id.ivHome)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val about: TextView = itemView.findViewById(R.id.tvAbout)
        val rate: TextView = itemView.findViewById(R.id.tvRate)


    }
}