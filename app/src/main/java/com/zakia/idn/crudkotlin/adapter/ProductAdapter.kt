package com.zakia.idn.crudkotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.zakia.idn.crudkotlin.R
import com.zakia.idn.crudkotlin.activity.ProductActivity
import com.zakia.idn.crudkotlin.model.PersonItem
import java.lang.String

class ProductAdapter(context: Context, resource: Int, objects: List<PersonItem>) :
    ArrayAdapter<PersonItem?>(context, resource, objects) {
    private val personItems: List<PersonItem>
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.list_item, parent, false)
        val txtIdProduct = v.findViewById<TextView>(R.id.txt_product_id)
        val txtNameProduct = v.findViewById<TextView>(R.id.txt_product_name)
        val txtPriceProduct = v.findViewById<TextView>(R.id.txt_product_price)
        val txtDescProduct = v.findViewById<TextView>(R.id.txt_product_desc)
        txtIdProduct.setText(String.valueOf(personItems[position].getId()))
        txtNameProduct.setText(String.valueOf(personItems[position].getName()))
        txtPriceProduct.setText(String.valueOf(personItems[position].getPrice()))
        txtDescProduct.setText(String.valueOf(personItems[position].getDesc()))
        v.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("id", String.valueOf(personItems[position].getId()))
            intent.putExtra("name", personItems[position].getName())
            intent.putExtra("price", personItems[position].getPrice())
            intent.putExtra("desc", personItems[position].getDesc())
            context.startActivity(intent)
        }
        return v
    }

    init {
        personItems = objects
    }
}