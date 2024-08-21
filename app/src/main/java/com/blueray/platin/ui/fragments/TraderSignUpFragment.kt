package com.blueray.platin.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.blueray.platin.databinding.FragmentTraderSignUpBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.LoginActivity
import com.blueray.platin.ui.activities.MainActivity
import com.blueray.platin.viewModels.AppViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class TraderSignUpFragment(override val viewModel: AppViewModel) :
    BaseFragment<FragmentTraderSignUpBinding, AppViewModel>() {

    private val countryIdMap = mutableMapOf<String, Int>()
    private val cityIdMap = mutableMapOf<String, Int>()
    var selectedCityId = ""
    var selectedCountryId = ""
    private var selectedCommercialRegisterFile: File? = null
    private var selectedProfessionLicenceFile: File? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTraderSignUpBinding {
        return FragmentTraderSignUpBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button clicks
        binding?.signUpButton?.setOnClickListener {
            if (validateForm()) {
                selectedCommercialRegisterFile?.let { it1 ->
                    selectedProfessionLicenceFile?.let { it2 ->
                        viewModel.retrieveTraderRegister(
                            first_name = binding?.traderFirstName?.text.toString(),
                            last_name = binding?.traderLastName?.text.toString(),
                            email = binding?.traderEmail?.text.toString(),
                            country_id = selectedCountryId.toString(),
                            city_id = selectedCityId.toString(),
                            street_name = binding?.traderStreet?.text.toString(),
                            building_name_number = binding?.traderBuilding?.text.toString(),
                            phone = binding?.traderPhone?.text.toString(),
                            password = binding?.traderPassword?.text.toString(),
                            password_confirmation = binding?.traderConfirmPassword?.text.toString(),
                            commerical_name = binding?.commercialName?.text.toString(),
                            commercial_register = it1,
                            profession_licence = it2
                        )
                    }
                }
            }
        }

        // Handle spinner selection for trader country
        binding?.traderCountry?.onItemSelectedListener =
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

        binding?.traderCity?.onItemSelectedListener =
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

        // Set up file pickers
        binding?.commercialRecord?.setOnClickListener {
            openFilePicker(REQUEST_CODE_PICK_COMMERCIAL_REGISTER)
        }

        binding?.tradingLicense?.setOnClickListener {
            openFilePicker(REQUEST_CODE_PICK_PROFESSION_LICENCE)
        }

        // Api calls
        viewModel.retrieveCountries()

        // Observe data
        getCountries()
        getCities()
        getTraderRegister()
    }

    // Constants for request codes
    companion object {
        private const val REQUEST_CODE_PICK_COMMERCIAL_REGISTER = 1001
        private const val REQUEST_CODE_PICK_PROFESSION_LICENCE = 1002
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri: Uri? = data.data
            when (requestCode) {
                REQUEST_CODE_PICK_COMMERCIAL_REGISTER -> {
                    uri?.let {
                        selectedCommercialRegisterFile = uriToFile(it)
                        binding?.commercialRecord?.text = selectedCommercialRegisterFile?.name
                    }
                }

                REQUEST_CODE_PICK_PROFESSION_LICENCE -> {
                    uri?.let {
                        selectedProfessionLicenceFile = uriToFile(it)
                        binding?.tradingLicense?.text = selectedProfessionLicenceFile?.name
                    }
                }
            }
        }
    }

    private fun openFilePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // Set the MIME type you want to support
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, requestCode)
    }

    private fun uriToFile(uri: Uri): File? {
        val inputStream = requireContext().contentResolver.openInputStream(uri) ?: return null
        val file = File(requireContext().cacheDir, "tempfile_${System.currentTimeMillis()}")
        try {
            val outputStream: OutputStream = FileOutputStream(file)
            inputStream.use { input ->
                outputStream.use { output ->
                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }

    // Functions to validate the fields
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
            validateField(binding!!.traderFirstName, "الاسم الأول مطلوب") { it.isNotEmpty() }
        val isLastNameValid =
            validateField(binding!!.traderLastName, "اسم العائلة مطلوب") { it.isNotEmpty() }
        val isPhoneValid = validateField(
            binding!!.traderPhone,
            "رقم الهاتف غير صالح"
        ) { it.isNotEmpty() && it.matches(Regex("^[+]?[0-9]{10,13}\$")) }
        val isEmailValid = validateField(
            binding!!.traderEmail,
            "البريد الإلكتروني غير صالح"
        ) { it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches() }
        val isStreetNameValid =
            validateField(binding!!.traderStreet, "اسم الشارع مطلوب") { it.isNotEmpty() }
        val isBuildingNumberValid =
            validateField(binding!!.traderBuilding, "رقم المبنى مطلوب") { it.isNotEmpty() }
        val isPasswordValid = validateField(
            binding!!.traderPassword,
            "كلمة المرور يجب أن تحتوي على حروف كبيرة وصغيرة ورمز خاص على الأقل وطولها لا يقل عن 8 أحرف"
        ) {
            it.isNotEmpty() && it.length >= 8 && it.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).*\$"))
        }
        val isConfirmPasswordValid = validateField(
            binding!!.traderConfirmPassword,
            "كلمتا المرور غير متطابقتين"
        ) { it == binding!!.traderPassword.text.toString() }
        val isCountryValid = binding!!.traderCountry.selectedItem != null
        if (!isCountryValid) (binding!!.traderCountry.selectedView as? TextView)?.error =
            "يرجى اختيار الدولة"
        val isCityValid = binding!!.traderCity.selectedItem != null
        if (!isCityValid) (binding!!.traderCity.selectedView as? TextView)?.error =
            "يرجى اختيار المدينة"

        // File validations
        val isCommercialRegisterValid = selectedCommercialRegisterFile != null
        if (!isCommercialRegisterValid) {
            binding!!.commercialRecord.error = "يرجى اختيار سجل تجاري"
        }

        val isProfessionLicenceValid = selectedProfessionLicenceFile != null
        if (!isProfessionLicenceValid) {
            binding!!.tradingLicense.error = "يرجى اختيار رخصة مهنية"
        }

        return isFirstNameValid && isLastNameValid && isPhoneValid && isEmailValid &&
                isPasswordValid && isConfirmPasswordValid && isCountryValid && isCityValid &&
                isStreetNameValid && isBuildingNumberValid && isCommercialRegisterValid &&
                isProfessionLicenceValid
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
                    binding?.traderCountry?.adapter = adapter
                }

                is NetworkResults.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "Failed to load countries",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    binding?.traderCity?.adapter = adapter
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }

    private fun getTraderRegister() {
        viewModel.getTraderRegister().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
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
}
