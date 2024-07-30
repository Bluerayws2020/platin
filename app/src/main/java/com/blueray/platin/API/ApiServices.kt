package com.blueray.platin.API


import com.blueray.platin.models.GetCitiesResponse
import com.blueray.platin.models.GetCountriesResponse
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {

    @POST("customer/getCountries")
    suspend fun getCountries(
        @Header("lang") lang: String
    ): GetCountriesResponse

    @Multipart
    @POST("customer/getCities")
    suspend fun getCities(
        @Header("lang") lang: String,
        @Part("country_id")country_id:RequestBody
    ):GetCitiesResponse
}