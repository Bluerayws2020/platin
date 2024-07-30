package com.blueray.platin.API

import com.blueray.platin.models.GetCitiesResponse
import com.blueray.platin.models.GetCountriesResponse
import com.blueray.platin.models.NetworkResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

object NetworkRepository {


    suspend fun getCountries(lang: String): NetworkResults<GetCountriesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getCountries(lang)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getCities(lang: String, country_id: String): NetworkResults<GetCitiesResponse> {
        return withContext(Dispatchers.IO) {
            val country_idBody = country_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            try {
                val results = ApiClient.retrofitService.getCities(lang, country_idBody)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
}