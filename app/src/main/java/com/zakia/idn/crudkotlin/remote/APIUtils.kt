package com.zakia.idn.crudkotlin.remote

object APIUtils {
    private fun APIUtils() {}

    val API_URL = "http://192.168.1.6/marketplace2/index.php/"
    fun getProductService(): ProductService? {
        return RetrofitClient.getClient(API_URL)?.create(ProductService::class.java)!!
    }
}