package com.blueray.platin.API

import android.util.Log
import com.blueray.platin.models.GetCategoriesResponse
import com.blueray.platin.models.GetCitiesResponse
import com.blueray.platin.models.GetColorsBySizeResponse
import com.blueray.platin.models.GetCountriesResponse
import com.blueray.platin.models.GetMyProfileResponse
import com.blueray.platin.models.GetProductDetailsResponse
import com.blueray.platin.models.GetProductsForCategoryResponse
import com.blueray.platin.models.GetSubCategoriesResponse
import com.blueray.platin.models.GetVariationPriceResponse
import com.blueray.platin.models.IndvisualRegisterResponse
import com.blueray.platin.models.LoginResponse
import com.blueray.platin.models.LogoutResponse
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.models.TraderRegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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

    suspend fun registerIndvisual(
        lang: String,
        first_name: String,
        last_name: String,
        email: String,
        street_name: String,
        building_name_number: String,
        country_id: String,
        city_id: String,
        password: String,
        password_confirmation: String,
        phone: String,
    ): NetworkResults<IndvisualRegisterResponse> {
        val first_nameBody = first_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val last_nameBody = last_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val emailBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val street_nameBody = street_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val building_name_numberBody =
            building_name_number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val country_idBody = country_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val city_idBody = city_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val password_confirmationBody =
            password_confirmation.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneBody = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.registerIndvisual(
                    lang = lang,
                    first_name = first_nameBody,
                    last_name = last_nameBody,
                    email = emailBody,
                    street_name = street_nameBody,
                    building_name_number = building_name_numberBody,
                    country_id = country_idBody,
                    city_id = city_idBody,
                    password = passwordBody,
                    password_confirmation = password_confirmationBody,
                    phone = phoneBody
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun traderRegister(
        lang: String,
        first_name: String,
        last_name: String,
        email: String,
        street_name: String,
        building_name_number: String,
        country_id: String,
        city_id: String,
        password: String,
        password_confirmation: String,
        phone: String,
        commerical_name: String,
        profession_licence: File,
        commercial_register: File,
    ): NetworkResults<TraderRegisterResponse> {
        val first_nameBody = first_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val last_nameBody = last_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val emailBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val street_nameBody = street_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val building_name_numberBody =
            building_name_number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val country_idBody = country_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val city_idBody = city_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val commerical_nameBody =
            commerical_name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val password_confirmationBody =
            password_confirmation.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneBody = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val profession_licenceBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), profession_licence)
        val profession_licence_part = MultipartBody.Part.createFormData(
            "profession_licence",
            profession_licence.name,
            profession_licenceBody
        )
        val commercial_registerBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), commercial_register)
        val commercial_register_part = MultipartBody.Part.createFormData(
            "commercial_register",
            commercial_register.name,
            commercial_registerBody
        )
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.registerTrader(
                    lang = lang,
                    first_name = first_nameBody,
                    last_name = last_nameBody,
                    email = emailBody,
                    street_name = street_nameBody,
                    building_name_number = building_name_numberBody,
                    country_id = country_idBody,
                    city_id = city_idBody,
                    password = passwordBody,
                    password_confirmation = password_confirmationBody,
                    phone = phoneBody,
                    commercial_register = commercial_register_part,
                    profession_licence = profession_licence_part,
                    commerical_name = commerical_nameBody
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun login(
        lang: String,
        emailOrPhone: String,
        password: String
    ): NetworkResults<LoginResponse> {
        val emailOrPhoneBody = emailOrPhone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.login(
                    lang,
                    emailOrPhoneBody,
                    passwordBody
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getMyProfile(
        lang: String,
        auth: String
    ): NetworkResults<GetMyProfileResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getMyProfile(lang, auth)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun logout(
        lang: String,
        auth: String
    ): NetworkResults<LogoutResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.logout(lang, auth)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun resetPasswordEmail(lang: String, email: String): NetworkResults<LogoutResponse> {
        val emailBody = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.sendPasswordResetEmail(lang, emailBody)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getCategories(lang: String): NetworkResults<GetCategoriesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getCategories(lang)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getSubCategories(
        lang: String,
        parent_id: Int
    ): NetworkResults<GetSubCategoriesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getSubCategories(lang, parent_id)
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getProductsForCategory(
        lang: String,
        category_id: Int,
        page: Int,
        per_page: Int,
        min: Float?,
        max: Float?
    ): NetworkResults<GetProductsForCategoryResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getProductsForCategory(
                    lang,
                    category_id,
                    page,
                    per_page,
                    min,
                    max
                )
                Log.d("NETWORKTEST", results.status.toString())
                NetworkResults.Success(results)

            } catch (e: Exception) {
                Log.d("NETWORKTEST", e.localizedMessage.toString())
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getProductDetails(
        lang: String,
        token: String,
        id: String
    ): NetworkResults<GetProductDetailsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getProductDetails(
                    lang,
                    "Bearer $token",
                    id
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getColorsBySize(
        lang: String,
        size_id: String,
        product_id: String
    ): NetworkResults<GetColorsBySizeResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getColorsBySize(
                    lang, size_id, product_id
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun getPriceOfTheVariation(
        lang: String,
        auth: String,
        size_id: String,
        product_id: String,
        color_id: String
    ): NetworkResults<GetVariationPriceResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val results = ApiClient.retrofitService.getPriceOfTheVariation(
                    lang,
                    auth,
                    size_id,
                    product_id,
                    color_id
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }

    suspend fun addToCart(
        lang: String,
        auth: String,
        product_id: String,
        size_id: String,
        color_id: String,
        quantity: String,
        variation_id:String?
    ): NetworkResults<LogoutResponse> {
        return withContext(Dispatchers.IO) {
            val product_idBody = product_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val size_idBody = size_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val color_idBody = color_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val quantityBody = quantity.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val variation_idBody = variation_id?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            try {
                val results = ApiClient.retrofitService.addToCart(
                    lang,
                    auth,
                    product_idBody,
                    size_idBody,
                    color_idBody,
                    quantityBody,
                    variation_idBody
                )
                NetworkResults.Success(results)
            } catch (e: Exception) {
                NetworkResults.Error(e)
            }
        }
    }
}