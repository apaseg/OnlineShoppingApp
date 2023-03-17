package com.example.onlineshopping.adapters

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.R
import com.example.onlineshopping.R.drawable
import com.example.onlineshopping.model.cartData
import com.example.onlineshopping.model.shoppingdata
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class shoppingAdapter(private val shoppingItemList: ArrayList<shoppingdata>) : RecyclerView.Adapter<shoppingAdapter.ViewHolder>() {

    private lateinit var dbCrtRef: DatabaseReference

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): shoppingAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: shoppingAdapter.ViewHolder, position: Int) {
        val currentItem = shoppingItemList[position]
        holder.title.text = currentItem.tvTitle
        holder.about.text = currentItem.tvAbout
        holder.rate.text = currentItem.tvRate
        holder.homeImage.setImageURI(currentItem.itemImage?.toUri())
        currentItem.ivCrtImage?.let { holder.crt.setImageResource(it) }
        currentItem.ivDeleteImage?.let { holder.dlt.setImageResource(it) }

        holder.crt.setOnClickListener {

            if (currentItem.ivCrtImage?.equals(R.drawable.ic_baseline_shopping_cart_24)!!) {
            dbCrtRef = FirebaseDatabase.getInstance().getReference("CartItem")
            val items = cartData(
                currentItem.itemId,
                currentItem.itemImage,
                currentItem.tvTitle,
                currentItem.tvAbout,
                currentItem.tvRate
            )

            dbCrtRef.child(currentItem.itemId!!).setValue(items)
                .addOnCompleteListener {
                    Toast.makeText(
                        holder.itemView.context,
                        "Data Inserted On Cart Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val dbRef = FirebaseDatabase.getInstance().getReference("shoppings")
                        .child(currentItem.itemId!!)
                    val itemInfo = shoppingdata(
                        currentItem.itemId,
                        currentItem.itemImage,
                        currentItem.tvTitle,
                        currentItem.tvAbout,
                        currentItem.tvRate,
                        R.drawable.ic_cart,
                        R.drawable.ic_delete
                    )
                    dbRef.setValue(itemInfo)
                }
                .addOnFailureListener { err ->
                    Toast.makeText(
                        holder.itemView.context,
                        "Error ${err.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        else{
            Toast.makeText(holder.itemView.context,"Already Added in Cart",Toast.LENGTH_SHORT).show()
            }
        }

        holder.dlt.setOnClickListener{
            val dbRef = FirebaseDatabase.getInstance().getReference("shoppings").child(currentItem.itemId!!)
            val mTast = dbRef.removeValue()

            mTast.addOnSuccessListener {
                Toast.makeText(holder.itemView.context,"Item Deleted",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { error->
                Toast.makeText(holder.itemView.context,"Deleting err ${error.message}",Toast.LENGTH_SHORT).show()
            }

            dbCrtRef = FirebaseDatabase.getInstance().getReference("CartItem").child(currentItem.itemId!!)
            dbCrtRef.removeValue()
        }
    }

    override fun getItemCount(): Int {

        return shoppingItemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val homeImage: ImageView = itemView.findViewById(R.id.ivHome)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val about: TextView = itemView.findViewById(R.id.tvAbout)
        val rate: TextView = itemView.findViewById(R.id.tvRate)
        val crt: ImageView = itemView.findViewById(R.id.ivCart)
        val dlt: ImageView = itemView.findViewById(R.id.ivDelete)
    }
}