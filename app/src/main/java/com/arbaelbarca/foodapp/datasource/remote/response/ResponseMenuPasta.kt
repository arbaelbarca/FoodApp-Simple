package com.arbaelbarca.foodapp.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseMenuPasta(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)

data class Nutrition(

	@field:SerializedName("nutrients")
	val nutrients: List<NutrientsItem?>? = null
)

data class NutrientsItem(

	@field:SerializedName("amount")
	val amount: Double? = null,

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class ResultsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("nutrition")
	val nutrition: Nutrition? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("imageType")
	val imageType: String? = null
)
