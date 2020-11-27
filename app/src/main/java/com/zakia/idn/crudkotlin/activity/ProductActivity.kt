package com.zakia.idn.crudkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zakia.idn.crudkotlin.R
import com.zakia.idn.crudkotlin.model.PersonItem
import com.zakia.idn.crudkotlin.remote.APIUtils
import com.zakia.idn.crudkotlin.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    var productService: ProductService? = null
    var edtName: EditText? = null
    var edtPrice: EditText? = null
    var edtDesc: EditText? = null
    var edtId: EditText? = null
    var btnSave: Button? = null
    var btnDelete: Button? = null
    var txtId: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        edtName = findViewById(R.id.et_name)
        edtPrice = findViewById(R.id.et_price)
        edtDesc = findViewById(R.id.et_desc)
        edtId = findViewById(R.id.et_id)
        txtId = findViewById(R.id.txt_id)
        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete)
        productService = APIUtils.getProductService()
        val extras = intent.extras
        val productId = extras!!.getString("id")
        val productName = extras.getString("name")
        val productPrice = extras.getString("price")
        val productDesc = extras.getString("desc")
        edtId?.setText(productId)
        edtName?.setText(productName)
        edtPrice?.setText(productPrice)
        edtDesc?.setText(productDesc)
        if (productId != null && productId.trim { it <= ' ' }.length > 0) {
            edtId?.setFocusable(false)
        } else {
            txtId?.setVisibility(View.INVISIBLE)
            edtId?.setVisibility(View.INVISIBLE)
            btnDelete?.setVisibility(View.INVISIBLE)
        }
        btnSave?.setOnClickListener(View.OnClickListener {
            val name = edtName?.getText().toString()
            val price = edtPrice?.getText().toString()
            val desc = edtDesc?.getText().toString()
            if (productId != null && productId.trim { it <= ' ' }.length > 0) {
                updateProduct(productId.toInt(), name, price, desc)
            } else {
                addProduct(name, price, desc)
            }
        })
        btnDelete?.setOnClickListener(View.OnClickListener {
            deleteProduct(productId!!.toInt())
            val intent = Intent(this@ProductActivity, MainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun addProduct(name: String, price: String, desc: String) {
        val call: Call<PersonItem?>? = productService!!.addProduct(name, price, desc)
        call?.enqueue(object : Callback<PersonItem?> {
            override fun onResponse(call: Call<PersonItem?>, response: Response<PersonItem?>) {
                if (response.isSuccessful()) {
                    Toast.makeText(this@ProductActivity, "Product added", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PersonItem?>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }
        })
    }

    private fun updateProduct(id: Int, name: String, price: String, desc: String) {
        val call: Call<PersonItem?>? = productService!!.updateProduct(id, name, price, desc)
        call?.enqueue(object : Callback<PersonItem?> {
            override fun onResponse(call: Call<PersonItem?>, response: Response<PersonItem?>) {
                Toast.makeText(this@ProductActivity, "Product Updated", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<PersonItem?>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }
        })
    }

    private fun deleteProduct(id: Int) {
        val call: Call<PersonItem?>? = productService!!.deleteProduct(id)
        call!!.enqueue(object : Callback<PersonItem?> {
            override fun onResponse(call: Call<PersonItem?>, response: Response<PersonItem?>) {
                if (response.isSuccessful()) {
                    Toast.makeText(this@ProductActivity, "Product Deleted", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<PersonItem?>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }
        })
    }
}