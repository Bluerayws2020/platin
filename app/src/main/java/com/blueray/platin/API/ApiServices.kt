package com.blueray.platin.API

import com.blueray.platin.models.RegisterIndvisualResponse
import com.blueray.platin.models.RegisterTraderResponse
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {

    @Multipart
    @POST("api/customer/registerIndvisual")
    suspend fun registerIndvisual(
        @Part("email") email: RequestBody,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("street_name") street_name: RequestBody,
        @Part("building_name_number") building_name_number: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("type") type: RequestBody,
        @Part("status") status: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") password_confirmation: RequestBody,
        @Part("phone") phone: RequestBody,
        ): RegisterIndvisualResponse

    @Multipart
    @POST("api/customer/registerTrader")
    suspend fun registerTrader(
        @Part("email") email: RequestBody,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("street_name") street_name: RequestBody,
        @Part("building_name_number") building_name_number: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") password_confirmation: RequestBody,
        @Part("phone") phone: RequestBody,

        @Part("commerical_name") commerical_name: RequestBody,
        @Part("profession_licence") profession_licence: RequestBody,
        @Part("commercial_register") commercial_register: RequestBody,
    ): RegisterTraderResponse


}