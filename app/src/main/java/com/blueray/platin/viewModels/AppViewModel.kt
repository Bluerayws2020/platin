package com.blueray.platin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blueray.platin.API.NetworkRepository
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.models.RegisterIndvisualResponse
import com.blueray.platin.models.RegisterTraderResponse
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NetworkRepository
    //private val lang = "ar"

//    private val lang = HelperUtils.getLang(application.applicationContext)
//    private val uid = HelperUtils.getUID(application.applicationContext)
//    private val token = HelperUtils.getToken(application.applicationContext)

    private val RegisterIndvisualResponseLiveData = MutableLiveData<NetworkResults<RegisterIndvisualResponse>>()
    private val RegisterTraderResponseLiveData = MutableLiveData<NetworkResults<RegisterTraderResponse>>()

    fun retrieveRegisterIndvisual(
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
    ) {
        viewModelScope.launch {
            RegisterIndvisualResponseLiveData.value = repo.registerIndvisual(
                email = email,
                first_name = first_name,
                last_name = last_name,
                street_name = street_name,
                building_name_number = building_name_number,
                country_id = country_id,
                city_id = city_id,
                type = type,
                status = status,
                password = password,
                password_confirmation = password_confirmation,
                phone = phone
            )
        }
    }
    fun getRegisterIndvisualResponse() = RegisterIndvisualResponseLiveData


    fun retrieveRegisterTrader(
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
    ) {
        viewModelScope.launch {
            RegisterTraderResponseLiveData.value = repo.registerTrader(
                email = email,
                first_name = first_name,
                last_name = last_name,
                street_name = street_name,
                building_name_number = building_name_number,
                country_id = country_id,
                city_id = city_id,
                password = password,
                password_confirmation = password_confirmation,
                phone = phone,
                commerical_name = commerical_name,
                profession_licence = profession_licence,
                commercial_register = commercial_register,
            )
        }
    }
    fun getRegisterTraderResponse() = RegisterTraderResponseLiveData
}