package com.arbaelbarca.foodapp.datasource.network

import com.arbaelbarca.foodapp.datasource.remote.response.ResponseMenuPasta
import com.google.android.gms.common.api.internal.ApiKey
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("recipes/complexSearch")
    suspend fun callMenuPasta(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String,
        @Query("maxFat") maxFat: Int,
        @Query("number") number: Int
    ): ResponseMenuPasta

}
