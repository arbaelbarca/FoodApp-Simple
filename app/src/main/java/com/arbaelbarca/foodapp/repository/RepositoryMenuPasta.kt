package com.arbaelbarca.foodapp.repository

import com.arbaelbarca.foodapp.datasource.network.ApiService
import com.arbaelbarca.foodapp.datasource.remote.response.ResponseMenuPasta
import com.arbaelbarca.foodapp.utils.ConstVar
import com.google.gson.JsonObject
import javax.inject.Inject

class RepositoryMenuPasta @Inject constructor(val apiService: ApiService) {

    suspend fun callApiPasta(textQuery: String, page: Int, perPage: Int): ResponseMenuPasta {
        return apiService.callMenuPasta(textQuery, ConstVar.apiKey, page, perPage)
    }
}