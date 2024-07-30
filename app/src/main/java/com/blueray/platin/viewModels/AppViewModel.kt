package com.blueray.platin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blueray.platin.API.NetworkRepository
import com.blueray.platin.models.GetCitiesResponse
import com.blueray.platin.models.GetCountriesResponse
import com.blueray.platin.models.NetworkResults

import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NetworkRepository
    private val lang = "ar"

    //    private val lang = HelperUtils.getLang(application.applicationContext)
//    private val uid = HelperUtils.getUID(application.applicationContext)
//    private val token = HelperUtils.getToken(application.applicationContext)
    private val getCountriesLiveData = MutableLiveData<NetworkResults<GetCountriesResponse>>()
    private val getCitiesLiveData = MutableLiveData<NetworkResults<GetCitiesResponse>>()

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


}