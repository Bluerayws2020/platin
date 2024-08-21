package com.blueray.platin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blueray.platin.databinding.ActivityLoginBinding
import com.blueray.platin.helpers.HelperUtils
import com.blueray.platin.models.LoginData
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.viewModels.AppViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if (validateForm()) {
                viewModel.retrieveLogin(
                    binding.email.text.toString(),
                    binding.password.text.toString(),
                )
            }

        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordTv.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        //Observe data
        getLogin()
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
        val isEmailValid =
            validateField(binding.email, "الايميل او رقم الهاتف مطلوب") { it.isNotEmpty() }
        val isPasswordValid =
            validateField(binding.password, "كلمة السر مطلوبة") { it.isNotEmpty() }
        return isEmailValid && isPasswordValid
    }

    //Observers
    private fun getLogin() {
        viewModel.getLogin().observe(this) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    saveUserData(result.data.data)
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                is NetworkResults.Error -> {
                    Toast.makeText(
                        this,
                        result.exception.localizedMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                }
            }
        }
    }

    fun saveUserData(model: LoginData) {
        val sharedPreferences = getSharedPreferences(HelperUtils.SHARED_PREF, MODE_PRIVATE)


        sharedPreferences.edit().apply {
            putString("uid", model.customer.id.toString())
            putString("token", model.token)
            putString("userName", model.customer.first_name + " " + model.customer.last_name)

        }.apply()
    }
}