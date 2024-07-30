package com.blueray.platin.models

data class GetCountriesResponse(
    val `data`: List<GetCountriesData>,
    val status: Int
)

data class GetCountriesData(
    val id: Int,
    val name: String,
    val phone_code: String,
    val status: String
)

data class GetCitiesResponse(
    val `data`: List<GetCitiesData>,
    val status: Int
)

data class GetCitiesData(
    val id: Int,
    val name: String
)

data class IndvisualRegisterResponse(
    val `data`: IndvisualRegisterData,
    val message: String,
    val status: Int
)
data class IndvisualRegisterData(
    val customer: Customer,
    val token: String
)

data class Customer(
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
    val updated_at: String
)
