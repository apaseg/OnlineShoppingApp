package com.example.onlineshopping.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.onlineshopping.R
import com.example.onlineshopping.model.shoppingdata
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Insertion: Fragment() {
    private lateinit var tvItem: TextView
    private lateinit var ivItem: ImageView
    private lateinit var ivCrt: ImageView
    private lateinit var etTitle: EditText
    private lateinit var etDiscription: EditText
    private lateinit var etAmount: EditText
    private lateinit var btnSave: Button
    private lateinit var ImageUri: Uri
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insertion,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvItem =  view.findViewById(R.id.tvItem)
        ivItem =  view.findViewById(R.id.ivItem)
        etTitle =  view.findViewById(R.id.etTitle)
        etDiscription =  view.findViewById(R.id.etDiscription)
        etAmount =  view.findViewById(R.id.etAmount)
        btnSave = view.findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("shoppings")
        ivItem.setOnClickListener{
            selectImage()
        }
        btnSave.setOnClickListener {
            shoppingDataSave()
        }
    }

    private fun selectImage(){
        val GALLERY_INTENT_CALLED = 1
        val GALLERY_KITKAT_INTENT_CALLED = 2

        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, GALLERY_INTENT_CALLED)
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED)
        }
    }

    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (Build.VERSION.SDK_INT < 19) {
            ImageUri = data?.data!!
        } else {
            ImageUri = data?.data!!
            val takeFlags: Int = (data.flags
                    and (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION))

            try {
                requireActivity().contentResolver.takePersistableUriPermission(ImageUri,takeFlags)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        ivItem.setImageURI(ImageUri)
    }

    private fun shoppingDataSave(){
        val itemTitle = etTitle.text.toString()
        val itemDiscription = etDiscription.text.toString()
        val itemAmount = etAmount.text.toString()
        val itemImage : String = ImageUri.toString()

        if(itemTitle.isEmpty()){
            etTitle.error = "Please Enter Title"
        }

        if(itemDiscription.isEmpty()){
            etDiscription.error = "Please write brief discription"
        }

        if(itemAmount.isEmpty()){
            etAmount.error = "Please Enter Amount"
        }

        val itemId = dbRef.push().key!!
        val items = shoppingdata(itemId,itemImage,itemTitle,itemDiscription,itemAmount,R.drawable.ic_baseline_shopping_cart_24)

        dbRef.child(itemId).setValue(items)
            .addOnCompleteListener {
                Toast.makeText(activity,"Data Inserted Successfully",Toast.LENGTH_SHORT).show()
                etTitle.text.clear()
                etDiscription.text.clear()
                etAmount.text.clear()
                ivItem.setImageURI(null)
            }
            .addOnFailureListener { err ->
                Toast.makeText(activity,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }
    }
}