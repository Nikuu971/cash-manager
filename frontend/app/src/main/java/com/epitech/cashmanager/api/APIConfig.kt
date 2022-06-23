package com.epitech.cashmanager.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import retrofit2.converter.scalars.ScalarsConverterFactory


object APIConfig {

    private var BASE_URL = "localhost"
    private var PORT = 80

    private var retrofit: Retrofit? = null


    var gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrofitClient(context: Context): Retrofit {


        val okHttpClient = OkHttpClient.Builder()
            .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://$BASE_URL:$PORT/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }
    
    fun setBaseUrl(baseUrl : String) {
        BASE_URL = baseUrl
    }

    fun getBaseUrl() : String{
        return BASE_URL
    }
}