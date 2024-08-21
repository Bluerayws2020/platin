package com.blueray.platin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blueray.platin.databinding.ActivityForgotPasswordBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.viewModels.AppViewModel

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendEmail.setOnClickListener {
            if (binding.email.text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(binding.email.text)
                    .matches()
            ) {
                viewModel.retrieveResetPasswordEmail(binding.email.text.toString())
            } else {
                binding.email.setError("البريد الإلكتروني غير صالح")
            }

        }
        getResetPasswordEmail()
    }

    private fun getResetPasswordEmail() {
        viewModel.getResetPasswordEmail().observe(this) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }
}