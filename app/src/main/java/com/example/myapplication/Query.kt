package com.example.myapplication


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Query {
    @GET("products")
    fun getProducts(): Call<List<Model>>
}