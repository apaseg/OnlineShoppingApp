package com.example.onlineshopping.adapters

import android.content.Intent
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
import com.example.onlineshopping.model.shoppingdata
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.coroutines.coroutineContext

class CartAdapter(private val cartItemList: ArrayList<cartData>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var dbCrtRef: DatabaseReference

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

        holder.ivCrtDelete.setOnClickListener {
            dbCrtRef = FirebaseDatabase.getInstance().getReference("CartItem").child(crntItem.itemCartId!!)
            val mTast = dbCrtRef.removeValue()

            mTast.addOnSuccessListener {
                Toast.makeText(holder.itemView.context,"Item Deleted From Cart",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { error->
                Toast.makeText(holder.itemView.context,"Deleting err ${error.message}",Toast.LENGTH_SHORT).show()
            }

            dbRef = FirebaseDatabase.getInstance().getReference("shoppings").child(crntItem.itemCartId!!)
            dbRef = FirebaseDatabase.getInstance().getReference("shoppings").child(crntItem.itemCartId!!)
            val itemInfo = shoppingdata(
                crntItem.itemCartId,
                crntItem.itemCartImage,
                crntItem.tvCartTitle,
                crntItem.tvCartAbout,
                crntItem.tvCartRate,
                R.drawable.ic_baseline_shopping_cart_24,
                R.drawable.ic_delete
            )
            dbRef.setValue(itemInfo)
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
        val ivCrtDelete: ImageView = itemView.findViewById(R.id.ivCrtDelete)
    }
}
