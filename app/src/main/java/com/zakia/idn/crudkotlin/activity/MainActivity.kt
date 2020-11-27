package com.zakia.idn.crudkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.zakia.idn.crudkotlin.R
import com.zakia.idn.crudkotlin.adapter.ProductAdapter
import com.zakia.idn.crudkotlin.model.PersonItem
import com.zakia.idn.crudkotlin.remote.APIUtils
import com.zakia.idn.crudkotlin.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    var btnAddUser: Button? = null
    var btnGetUser: Button? = null
    var rv: ListView? = null
    var productService: ProductService? = null
    var list: List<PersonItem> = ArrayList<PersonItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAddUser = findViewById(R.id.btn_addUser)
        btnGetUser = findViewById(R.id.btn_getUserList)
        rv = findViewById(R.id.rv_main)
        productService = APIUtils.getProductService()
        btnAddUser?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, ProductActivity::class.java)
            intent.putExtra("name", "")
            intent.putExtra("price", "")
            intent.putExtra("desc", "")
            startActivity(intent)
        })
        btnGetUser?.setOnClickListener(View.OnClickListener { userList })
    }

    val userList: Unit get() {
            val call: Call<List<PersonItem>>? = productService?.getProduct()
            call!!.enqueue(object : Callback<List<PersonItem>> {
                override fun onResponse(
                    call: Call<List<PersonItem>>,
                    response: Response<List<PersonItem>>
                ) {
                    if (response.isSuccessful) {
                        list = response.body()!!
                        rv!!.adapter = ProductAdapter(this@MainActivity, R.layout.list_item, list)
                    }
                }

                override fun onFailure(call: Call<List<PersonItem>>, t: Throwable) {
                    Log.e("ERROR: ", t.message)
                }
            })
        }
}