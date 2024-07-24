package com.blueray.platin.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.blueray.platin.R
import com.blueray.platin.databinding.ActivitySignUpBinding
import com.blueray.platin.helpers.HelperUtils
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.models.RegisterIndvisualData
import com.blueray.platin.models.RegisterTraderData
import com.blueray.platin.viewModels.AppViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: AppViewModel by viewModels()
    var TYPE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.indevidual.setOnClickListener {
            TYPE = 1
            binding.indevidual.setBackgroundColor(resources.getColor(R.color.white))
            binding.indevidual.setStrokeColorResource(R.color.orange)
            binding.indevidual.setTextColor(resources.getColor(R.color.blue))

            binding.commircer.setBackgroundColor(resources.getColor(R.color.blue))
            binding.commircer.setStrokeColorResource(R.color.gray)
            binding.commircer.setTextColor(resources.getColor(R.color.gray))

            binding.commericalName.isVisible = false
            binding.professionLicence.isVisible = false
            binding.commercialRegister.isVisible = false
        }

        binding.commircer.setOnClickListener {
            TYPE = 2
            binding.commircer.setBackgroundColor(resources.getColor(R.color.white))
            binding.commircer.setStrokeColorResource(R.color.orange)
            binding.commircer.setTextColor(resources.getColor(R.color.blue))

            binding.indevidual.setBackgroundColor(resources.getColor(R.color.blue))
            binding.indevidual.setStrokeColorResource(R.color.gray)
            binding.indevidual.setTextColor(resources.getColor(R.color.gray))

            binding.commericalName.isVisible = true
            binding.professionLicence.isVisible = true
            binding.commercialRegister.isVisible = true
        }

        binding.signUpButton.setOnClickListener {
            if(TYPE == 1) {
                registerIndvisualData()
                validateFieldsIndevidual()
            } else {
                registerTraderData()
                validateFieldsTrader()
            }
        }

    }

    private fun validateFieldsIndevidual() {
        if (binding.email.text.isEmpty()) {
            binding.email.setError("الحقل هذا مطلوب")
        } else if (binding.name.text.isEmpty()) {
            binding.name.setError("الحقل هذا مطلوب")
        } else if (binding.password.text.isEmpty()) {
            binding.password.setError("الحقل هذا مطلوب")
        } else if (binding.confPassword.text.isEmpty()) {
            binding.confPassword.setError("الحقل هذا مطلوب")
        } else if (binding.phone.text.isEmpty()) {
            binding.phone.setError("الحقل هذا مطلوب")
        } else if (binding.password.text.toString() != binding.confPassword.text.toString()) {
            Toast.makeText(this, "كلمة السر غير متطابقة", Toast.LENGTH_LONG).show()
        } else {
            viewModel.retrieveRegisterIndvisual(
                binding.email.text.toString(),
                binding.name.text.toString(),
                " ",
                "12",
                "12",
                "1",
                "1",
                "1",
                "1",
                binding.password.text.toString(),
                binding.confPassword.text.toString(),
                binding.phone.text.toString()
            )
        }
    }

    private fun validateFieldsTrader() {
        if (binding.email.text.isEmpty()) {
            binding.email.setError("الحقل هذا مطلوب")
        } else if (binding.name.text.isEmpty()) {
            binding.name.setError("الحقل هذا مطلوب")
        } else if (binding.password.text.isEmpty()) {
            binding.password.setError("الحقل هذا مطلوب")
        } else if (binding.confPassword.text.isEmpty()) {
            binding.confPassword.setError("الحقل هذا مطلوب")
        } else if (binding.phone.text.isEmpty()) {
            binding.phone.setError("الحقل هذا مطلوب")

        } else if (binding.commericalName.text.isEmpty()) {
            binding.commericalName.setError("الحقل هذا مطلوب")
        } else if (binding.professionLicence.text.isEmpty()) {
            binding.professionLicence.setError("الحقل هذا مطلوب")
        } else if (binding.commercialRegister.text.isEmpty()) {
            binding.commercialRegister.setError("الحقل هذا مطلوب")


        } else if (binding.password.text.toString() != binding.confPassword.text.toString()) {
            Toast.makeText(this, "كلمة السر غير متطابقة", Toast.LENGTH_LONG).show()
        } else {
            viewModel.retrieveRegisterTrader(
                binding.email.text.toString(),
                binding.name.text.toString(),
                " ",
                "12",
                "12",
                "1",
                "1",
                binding.password.text.toString(),
                binding.confPassword.text.toString(),
                binding.phone.text.toString(),
                "",
                "",
                "",
            )
        }
    }

    private fun registerIndvisualData() {
        viewModel.getRegisterIndvisualResponse().observe(this) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.status == 200) {
                        saveIndvisualData(result.data.data, result.data.token)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, result.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                is NetworkResults.Error -> {
                    Toast.makeText(this, result.exception.message.toString(), Toast.LENGTH_LONG).show()
                }
                is NetworkResults.NoInternet -> {
                }
            }
        }
    }

    private fun registerTraderData() {
        viewModel.getRegisterTraderResponse().observe(this) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.status == 200) {
                        saveTraderData(result.data.data, result.data.token)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, result.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                is NetworkResults.Error -> {
                    Toast.makeText(this, result.exception.message.toString(), Toast.LENGTH_LONG).show()
                }
                is NetworkResults.NoInternet -> {
                }
            }
        }
    }

    fun saveIndvisualData(user: RegisterIndvisualData, token: String) {
        val sharedPreferences = getSharedPreferences(HelperUtils.SHARED_PREF, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("uid", user.id.toString())
        editor.putString("user_name", user.first_name)
        editor.putString("token", token)
        editor.apply()
    }

    fun saveTraderData(user: RegisterTraderData, token: String) {
        val sharedPreferences = getSharedPreferences(HelperUtils.SHARED_PREF, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("uid", user.id.toString())
        editor.putString("user_name", user.first_name)
        editor.putString("token", token)
        editor.apply()
    }
}