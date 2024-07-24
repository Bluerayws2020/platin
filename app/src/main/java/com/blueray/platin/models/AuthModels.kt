package com.blueray.platin.models

import com.google.gson.annotations.SerializedName


data class RegisterIndvisualResponse(
    @SerializedName("data")val `data`: RegisterIndvisualData,
    @SerializedName("status")val status: Int,
    @SerializedName("success")val success: Boolean,
    @SerializedName("token")val token: String,
    @SerializedName("message")val message: String
)

data class RegisterIndvisualData(
    val building_name_number: String,
    val city_id: String,
    val country_id: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val status: String,
    val street_name: String,
    val type: String,
    val updated_at: String,
)

data class RegisterTraderResponse(
    @SerializedName("data")val `data`: RegisterTraderData,
    @SerializedName("status")val status: Int,
    @SerializedName("success")val success: Boolean,
    @SerializedName("token")val token: String,
    @SerializedName("message")val message: String
)

data class RegisterTraderData(
    val building_name_number: String,
    val city_id: String,
    val country_id: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val status: String,
    val street_name: String,
    val type: String,
    val updated_at: String,
    val profession_licence: String,
    val commercial_register: String,
    val profession_licence_url: String,
    val commercial_register_url: String,
)