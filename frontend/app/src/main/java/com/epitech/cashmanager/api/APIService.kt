package com.epitech.cashmanager.api

import com.epitech.cashmanager.article.Article
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/products")
    fun getProducts(): Call<JsonObject>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/payment")
    fun postPayment(): Call<JsonObject>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/profile")
    fun userLogin(): Call<JsonObject>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/shoppingBaskets")
    fun getShoppingBaskets(): Call<JsonObject>

}