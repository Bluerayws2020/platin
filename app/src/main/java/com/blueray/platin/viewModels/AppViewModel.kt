package com.blueray.platin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blueray.platin.API.NetworkRepository
import com.blueray.platin.helpers.HelperUtils
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
import kotlinx.coroutines.launch
import java.io.File

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NetworkRepository
    private val lang = "ar"

    //    private val lang = HelperUtils.getLang(application.applicationContext)
//    private val uid = HelperUtils.getUID(application.applicationContext)
    private val token = HelperUtils.getToken(application.applicationContext)
    private val getCountriesLiveData = MutableLiveData<NetworkResults<GetCountriesResponse>>()
    private val getCitiesLiveData = MutableLiveData<NetworkResults<GetCitiesResponse>>()
    private val indvisualRegisterLiveData =
        MutableLiveData<NetworkResults<IndvisualRegisterResponse>>()
    private val traderRegisterLiveData = MutableLiveData<NetworkResults<TraderRegisterResponse>>()
    private val loginLiveData = MutableLiveData<NetworkResults<LoginResponse>>()
    private val logoutLiveData = MutableLiveData<NetworkResults<LogoutResponse>>()
    private val getMyProfileLiveData = MutableLiveData<NetworkResults<GetMyProfileResponse>>()
    private val resetPasswordEmailLiveData = MutableLiveData<NetworkResults<LogoutResponse>>()
    private val getCategoriesLiveData = MutableLiveData<NetworkResults<GetCategoriesResponse>>()
    private val getSubCategoriesLivaData =
        MutableLiveData<NetworkResults<GetSubCategoriesResponse>>()
    private val getProductsForCategoryLiveData =
        MutableLiveData<NetworkResults<GetProductsForCategoryResponse>>()
    private val getProductDetailsLiveData =
        MutableLiveData<NetworkResults<GetProductDetailsResponse>>()
    private val getColorsBySizeLiveData = MutableLiveData<NetworkResults<GetColorsBySizeResponse>>()
    private val getVariationPriceLiveData =
        MutableLiveData<NetworkResults<GetVariationPriceResponse>>()
    private val addToCartLiveData = MutableLiveData<NetworkResults<LogoutResponse>>()

    //****************** getCountries *************************//
    fun retrieveCountries() {
        viewModelScope.launch {
            getCountriesLiveData.postValue(repo.getCountries(lang))
        }
    }

    fun getCountries() = getCountriesLiveData
    //*******************************************************//


    //****************** getCities *************************//
    fun retrieveCities(country_id: String) {
        viewModelScope.launch {
            getCitiesLiveData.postValue(
                repo.getCities(
                    lang,
                    country_id
                )
            )
        }
    }

    fun getCities() = getCitiesLiveData
    //*******************************************************//


    //****************** registerIndvisual *************************//
    fun retrieveIndvisualRegister(
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
    ) {
        viewModelScope.launch {
            indvisualRegisterLiveData.postValue(
                repo.registerIndvisual(
                    lang = lang,
                    first_name = first_name,
                    last_name = last_name,
                    email = email,
                    street_name = street_name,
                    building_name_number = building_name_number,
                    country_id = country_id,
                    city_id = city_id,
                    password = password,
                    password_confirmation = password_confirmation,
                    phone = phone
                )
            )
        }
    }

    fun getIndvisualRegister() = indvisualRegisterLiveData
    //*******************************************************//


    //****************** registerTrader *************************//
    fun retrieveTraderRegister(
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

        ) {
        viewModelScope.launch {
            traderRegisterLiveData.postValue(
                repo.traderRegister(
                    lang = lang,
                    first_name = first_name,
                    last_name = last_name,
                    email = email,
                    street_name = street_name,
                    building_name_number = building_name_number,
                    country_id = country_id,
                    city_id = city_id,
                    password = password,
                    password_confirmation = password_confirmation,
                    phone = phone,
                    commerical_name = commerical_name,
                    commercial_register = commercial_register,
                    profession_licence = profession_licence
                )
            )
        }
    }

    fun getTraderRegister() = traderRegisterLiveData
    //*******************************************************//


    //****************** login *************************//
    fun retrieveLogin(
        emailOrPhone: String,
        password: String
    ) {
        viewModelScope.launch {
            loginLiveData.postValue(repo.login(lang, emailOrPhone, password))
        }
    }

    fun getLogin() = loginLiveData
    //*******************************************************//


    //****************** getMyProfile *************************//
    fun retrieveMyProfile() {
        val auth = "Bearer $token"
        viewModelScope.launch {
            getMyProfileLiveData.postValue(repo.getMyProfile(lang, auth))
        }
    }

    fun getMyProfile() = getMyProfileLiveData
    //*******************************************************//


    //****************** logout *************************//
    fun retrieveLogout() {
        val auth = "Bearer $token"
        viewModelScope.launch {
            logoutLiveData.postValue(repo.logout(lang, auth))
        }
    }

    fun getLogout() = logoutLiveData
    //*******************************************************//


    //****************** sendPasswordResetEmail *************************//
    fun retrieveResetPasswordEmail(email: String) {
        viewModelScope.launch {
            resetPasswordEmailLiveData.postValue(repo.resetPasswordEmail(lang, email))
        }
    }

    fun getResetPasswordEmail() = resetPasswordEmailLiveData
    //*******************************************************//


    //****************** getCategories *************************//
    fun retrieveCategories() {
        viewModelScope.launch {
            getCategoriesLiveData.postValue(repo.getCategories(lang))
        }
    }

    fun getCategories() = getCategoriesLiveData
    //*******************************************************//


    //****************** getSubCategoriesForSpesificCategory *************************//
    fun retrieveSubCategories(parent_id: Int) {
        viewModelScope.launch {
            getSubCategoriesLivaData.postValue(repo.getSubCategories(lang, parent_id))
        }
    }

    fun getSubCategories() = getSubCategoriesLivaData
    //*******************************************************//

    fun retrieveProductsForCategory(
        category_id: Int,
        page: Int,
        per_page: Int,
        min: Float?,
        max: Float?
    ) {
        viewModelScope.launch {
            getProductsForCategoryLiveData.postValue(
                repo.getProductsForCategory(
                    lang,
                    category_id,
                    page,
                    per_page,
                    min,
                    max
                )
            )
        }

    }

    fun getProductsForCategory() = getProductsForCategoryLiveData


    fun retrieveProductDetails(id: String) {
        viewModelScope.launch {
            getProductDetailsLiveData.postValue(
                repo.getProductDetails(
                    lang,
                    token,
                    id
                )
            )
        }
    }

    fun getProductDetails() = getProductDetailsLiveData


    fun retrieveColorsBySize(
        size_id: String,
        product_id: String
    ) {
        viewModelScope.launch {
            getColorsBySizeLiveData.postValue(
                repo.getColorsBySize(
                    lang, size_id, product_id
                )
            )
        }
    }

    fun getColorsBySize() = getColorsBySizeLiveData

    fun retrieveVariationPrice(
        size_id: String,
        product_id: String,
        color_id: String
    ) {
        viewModelScope.launch {
            val auth = "Bearer $token"
            getVariationPriceLiveData.postValue(
                repo.getPriceOfTheVariation(
                    lang,
                    auth,
                    size_id,
                    product_id,
                    color_id
                )
            )
        }
    }

    fun getVariationPrice() = getVariationPriceLiveData

    fun retrieveAddToCart(
        product_id: String,
        size_id: String,
        color_id: String,
        quantity: String,
        variation_id: String?
    ) {
        viewModelScope.launch {
            val auth = "Bearer $token"
            addToCartLiveData.postValue(
                repo.addToCart(
                    lang,
                    auth,
                    product_id,
                    size_id,
                    color_id,
                    quantity,
                    variation_id
                )
            )
        }
    }

    fun getAddToCart() = addToCartLiveData
}