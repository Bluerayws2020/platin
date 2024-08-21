package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.blueray.platin.databinding.FragmentIndividaulSipgUpBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MainActivity
import com.blueray.platin.viewModels.AppViewModel


class IndividualSignUpFragment(override val viewModel: AppViewModel) :
    BaseFragment<FragmentIndividaulSipgUpBinding, AppViewModel>() {
    private val countryIdMap = mutableMapOf<String, Int>()
    private val cityIdMap = mutableMapOf<String, Int>()
    var selectedCityId = ""
    var selectedCountryId = ""
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndividaulSipgUpBinding {
        return FragmentIndividaulSipgUpBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Button clicks
        binding?.signUpButton?.setOnClickListener {
            if (validateForm()) {
                viewModel.retrieveIndvisualRegister(
                    first_name = binding?.individualFirstName?.text.toString(),
                    last_name = binding?.individualLastName?.text.toString(),
                    email = binding?.individualEmail?.text.toString(),
                    country_id = selectedCountryId.toString(),
                    city_id = selectedCityId.toString(),
                    street_name = binding?.individualStreet?.text.toString(),
                    building_name_number = binding?.individualBuilding?.text.toString(),
                    phone = binding?.individualPhone?.text.toString(),
                    password = binding?.individualPassword?.text.toString(),
                    password_confirmation = binding?.individualConfirmPassword?.text.toString()
                )
            }

        }

        // Handle spinner selection for individual country
        binding?.individualCountry?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCountryName = parent.getItemAtPosition(position).toString()
                    selectedCountryId = countryIdMap[selectedCountryName].toString()
                    Toast.makeText(
                        requireActivity(),

                        "Selected ID: $selectedCountryId",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.retrieveCities(selectedCountryId.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

        binding?.individualCity?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCityName = parent.getItemAtPosition(position).toString()
                    selectedCityId = cityIdMap[selectedCityName].toString()
                    Toast.makeText(
                        requireActivity(),

                        "Selected ID: $selectedCityId",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }
        //Api calls
        viewModel.retrieveCountries()

        //Observe data
        getCountries()
        getCities()
        getIndvisualRegister()
    }

    //Functions to validate the fields
    private fun validateField(
        field: EditText,
        errorMessage: String,
        validationLogic: (String) -> Boolean
    ): Boolean {
        val text = field.text.toString()
        return if (!validationLogic(text)) {
            field.error = errorMessage
            false
        } else {
            field.error = null
            true
        }
    }

    private fun validateForm(): Boolean {
        val isFirstNameValid =
            validateField(binding!!.individualFirstName, "الاسم الأول مطلوب") { it.isNotEmpty() }
        val isLastNameValid =
            validateField(binding!!.individualLastName, "اسم العائلة مطلوب") { it.isNotEmpty() }
        val isPhoneValid = validateField(
            binding!!.individualPhone,
            "رقم الهاتف غير صالح"
        ) { it.isNotEmpty() && it.matches(Regex("^[+]?[0-9]{10,13}\$")) }
        val isEmailValid = validateField(
            binding!!.individualEmail,
            "البريد الإلكتروني غير صالح"
        ) { it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches() }
        val isStreetNameValid =
            validateField(binding!!.individualStreet, "اسم الشارع مطلوب") { it.isNotEmpty() }
        val isBuildingNumberValid =
            validateField(binding!!.individualBuilding, "رقم المبنى مطلوب") { it.isNotEmpty() }
        val isPasswordValid = validateField(
            binding!!.individualPassword,
            "كلمة المرور يجب أن تحتوي على حروف كبيرة وصغيرة ورمز خاص على الأقل وطولها لا يقل عن 8 أحرف"
        ) {
            it.isNotEmpty() && it.length >= 8 && it.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).*\$"))
        }
        val isConfirmPasswordValid = validateField(
            binding!!.individualConfirmPassword,
            "كلمتا المرور غير متطابقتين"
        ) { it == binding!!.individualPassword.text.toString() }
        val isCountryValid = binding!!.individualCountry.selectedItem != null
        if (!isCountryValid) (binding!!.individualCountry.selectedView as? TextView)?.error =
            "يرجى اختيار الدولة"
        val isCityValid = binding!!.individualCity.selectedItem != null
        if (!isCityValid) (binding!!.individualCity.selectedView as? TextView)?.error =
            "يرجى اختيار المدينة"

        return isFirstNameValid && isLastNameValid && isPhoneValid && isEmailValid && isPasswordValid && isConfirmPasswordValid && isCountryValid && isCityValid && isStreetNameValid && isBuildingNumberValid
    }

    // Observers
    private fun getCountries() {
        viewModel.getCountries().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    val countryNames = result.data.data.map { it.name }
                    countryIdMap.clear()
                    result.data.data.forEach { country ->
                        countryIdMap[country.name] = country.id
                    }
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_item,
                        countryNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding?.individualCountry?.adapter = adapter

                }

                is NetworkResults.Error -> {
                    // Handle error case
                }

                else -> {

                }
            }
        }
    }

    private fun getCities() {
        viewModel.getCities().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    val cityNames = result.data.data.map { it.name }
                    cityIdMap.clear()
                    result.data.data.forEach { city ->
                        cityIdMap[city.name] = city.id
                    }
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_item,
                        cityNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding?.individualCity?.adapter = adapter
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }

    private fun getIndvisualRegister() {
        viewModel.getIndvisualRegister().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                is NetworkResults.Error -> {
                    toast(result.exception.localizedMessage.toString())
                }

                else -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}