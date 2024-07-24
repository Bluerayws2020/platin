package com.blueray.platin.API

import com.blueray.platin.models.NetworkResults
import com.blueray.platin.models.RegisterIndvisualResponse
import com.blueray.platin.models.RegisterTraderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

object NetworkRepository {

    suspend fun registerIndvisual(
        email: String,
        first_name: String,
        last_name: String,
        street_name: String,
        building_name_number: String,
        country_id: String,
        city_id: String,
        type: String,
        status: String,
        password: String,
        password_confirmation: String,
        phone: String,
    ): NetworkResults<RegisterIndvisualResponse> {
        return withContext(Dispatchers.IO) {
            val emailBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val first_nameBody = first_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val last_nameBody = last_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val street_nameBody = street_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val building_name_numberBody = building_name_number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val country_idBody = country_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val city_idBody = city_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val typeBody = type.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val statusBody = status.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val password_confirmationBody = password_confirmation.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val phoneBody = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            try {
                val results = ApiClient.retrofitService.registerIndvisual(
                    emailBody,
                    first_nameBody,
                    last_nameBody,
                    street_nameBody,
                    building_name_numberBody,
                    country_idBody,
                    city_idBody,
                    typeBody,
                    statusBody,
                    passwordBody,
                    password_confirmationBody,
                    phoneBody,
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun registerTrader(
        email: String,
        first_name: String,
        last_name: String,
        street_name: String,
        building_name_number: String,
        country_id: String,
        city_id: String,
        password: String,
        password_confirmation: String,
        phone: String,
        commerical_name: String,
        profession_licence: String,
        commercial_register: String,
    ): NetworkResults<RegisterTraderResponse> {
        return withContext(Dispatchers.IO) {
            val emailBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val first_nameBody = first_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val last_nameBody = last_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val street_nameBody = street_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val building_name_numberBody = building_name_number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val country_idBody = country_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val city_idBody = city_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val password_confirmationBody = password_confirmation.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val phoneBody = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val commerical_nameBody = commerical_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val profession_licenceBody = profession_licence.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val commercial_registerBody = commercial_register.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            try {
                val results = ApiClient.retrofitService.registerTrader(
                    emailBody,
                    first_nameBody,
                    last_nameBody,
                    street_nameBody,
                    building_name_numberBody,
                    country_idBody,
                    city_idBody,
                    passwordBody,
                    password_confirmationBody,
                    phoneBody,
                    commerical_nameBody,
                    profession_licenceBody,
                    commercial_registerBody,
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
}