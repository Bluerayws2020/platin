package com.blueray.platin.API


import com.blueray.platin.models.AboutUsResponse
import com.blueray.platin.models.GetBrandsResponse
import com.blueray.platin.models.GetCategoriesResponse
import com.blueray.platin.models.GetCertificationsResponse
import com.blueray.platin.models.GetCitiesResponse
import com.blueray.platin.models.GetColorsBySizeResponse
import com.blueray.platin.models.GetCountriesResponse
import com.blueray.platin.models.GetMyCartResponse
import com.blueray.platin.models.GetMyProfileResponse
import com.blueray.platin.models.GetOurCompaniesResponse
import com.blueray.platin.models.GetProductDetailsResponse
import com.blueray.platin.models.GetProductsForCategoryResponse
import com.blueray.platin.models.GetRandomOffersResponse
import com.blueray.platin.models.GetSubCategoriesResponse
import com.blueray.platin.models.GetVariationPriceResponse
import com.blueray.platin.models.IndvisualRegisterResponse
import com.blueray.platin.models.LoginResponse
import com.blueray.platin.models.LogoutResponse
import com.blueray.platin.models.TraderRegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServices {

    @POST("customer/getCountries")
    suspend fun getCountries(
        @Header("lang") lang: String
    ): GetCountriesResponse

    @Multipart
    @POST("customer/getCities")
    suspend fun getCities(
        @Header("lang") lang: String,
        @Part("country_id") country_id: RequestBody
    ): GetCitiesResponse

    @Multipart
    @POST("customer/registerIndvisual")
    suspend fun registerIndvisual(
        @Header("lang") lang: String,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("street_name") street_name: RequestBody,
        @Part("building_name_number") building_name_number: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") password_confirmation: RequestBody,
        @Part("phone") phone: RequestBody,

        ): IndvisualRegisterResponse

    @Multipart
    @POST("customer/registerTrader")
    suspend fun registerTrader(
        @Header("lang") lang: String,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("street_name") street_name: RequestBody,
        @Part("building_name_number") building_name_number: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") password_confirmation: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("commerical_name") commerical_name: RequestBody,
        @Part profession_licence: MultipartBody.Part,
        @Part commercial_register: MultipartBody.Part
    ): TraderRegisterResponse

    @Multipart
    @POST("customer/login")
    suspend fun login(
        @Header("lang") lang: String,
        @Part("emailOrPhone") emailOrPhone: RequestBody,
        @Part("password") password: RequestBody,

        ): LoginResponse

    @POST("customer/getMyProfile")
    suspend fun getMyProfile(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String
    ): GetMyProfileResponse

    @POST("customer/logout")
    suspend fun logout(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String
    ): LogoutResponse

    @Multipart
    @POST("customer/sendPasswordResetEmail")
    suspend fun sendPasswordResetEmail(
        @Header("lang") lang: String,
        @Part("email") email: RequestBody
    ): LogoutResponse

    @GET("getCategories")
    suspend fun getCategories(
        @Header("lang") lang: String,
    ): GetCategoriesResponse

    @GET("getSubCategoriesForSpesificCategory")
    suspend fun getSubCategories(
        @Header("lang") lang: String,
        @Query("parent_id") parent_id: Int
    ): GetSubCategoriesResponse

    @GET("getProductsForSpecificCategory")
    suspend fun getProductsForCategory(
        @Header("lang") lang: String,
        @Query("category_id") category_id: Int,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("min") min: Float?,
        @Query("max") max: Float?,
        @Query("search") search: String?
    ): GetProductsForCategoryResponse

    @GET("getProductDetails")
    suspend fun getProductDetails(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Query("id") id: String
    ): GetProductDetailsResponse

    @GET("getColorsBySize")
    suspend fun getColorsBySize(
        @Header("lang") lang: String,
        @Query("size_id") size_id: String,
        @Query("product_id") product_id: String
    ): GetColorsBySizeResponse


    @GET("getPriceOfTheVariation")
    suspend fun getPriceOfTheVariation(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Query("size_id") size_id: String,
        @Query("product_id") product_id: String,
        @Query("color_id") color_id: String
    ): GetVariationPriceResponse

    @Multipart
    @POST("customer/addToCart")
    suspend fun addToCart(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Part("product_id") product_id: RequestBody,
        @Part("size_id") size_id: RequestBody,
        @Part("color_id") color_id: RequestBody,
        @Part("quantity") quantity: RequestBody,
        @Part("variation_id") variation_id: RequestBody?,
    ): LogoutResponse

    @POST("customer/getMyCart")
    suspend fun getMyCart(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
    ): GetMyCartResponse

    @Multipart
    @POST("customer/checkCoupon")
    suspend fun checkCoupon(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Part("coupon_code") coupon_code: RequestBody
    ): LogoutResponse

    @Multipart
    @POST("customer/checkout")
    suspend fun checkout(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Part("coupon_code") coupon_code: RequestBody
    ): LogoutResponse

    @Multipart
    @POST("customer/deleteFromCart")
    suspend fun deleteFromCart(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Part("cart_temp_id") cart_temp_id: RequestBody
    ): LogoutResponse

    @Multipart
    @POST("customer/updateCartQuantity")
    suspend fun updateCartQuantity(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Part("cart_temp_id") cart_temp_id: RequestBody,
        @Part("quantity") quantity: RequestBody,
    ): LogoutResponse

    @Multipart
    @POST("customer/rateThisProduct")
    suspend fun rateProduct(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
        @Part("product_id") product_id: RequestBody,
        @Part("review_value") review_value: RequestBody,
    ): LogoutResponse

    @GET("getAboutUs")
    suspend fun getAboutUs(
        @Header("lang") lang: String
    ): AboutUsResponse

    @GET("getCertifications")
    suspend fun getCertifications(
        @Header("lang") lang: String
    ): GetCertificationsResponse

    @GET("getOurCompanies")
    suspend fun getOurCompanies(
        @Header("lang") lang: String
    ): GetOurCompaniesResponse

    @GET("getRandomOffers")
    suspend fun getRandomOffers(
        @Header("lang") lang: String,
        @Header("Authorization") Authorization: String,
    ): GetRandomOffersResponse

    @GET("getBrands")
    suspend fun getBrands(
        @Header("lang") lang: String,
    ): GetBrandsResponse
}
