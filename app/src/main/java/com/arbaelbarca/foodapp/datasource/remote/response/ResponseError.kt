package com.arbaelbarca.foodapp.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

