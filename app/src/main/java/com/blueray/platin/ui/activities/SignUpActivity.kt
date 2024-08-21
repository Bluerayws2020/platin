package com.blueray.platin.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.blueray.platin.R
import com.blueray.platin.databinding.ActivitySignUpBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.fragments.IndividualSignUpFragment
import com.blueray.platin.ui.fragments.TraderSignUpFragment
import com.blueray.platin.viewModels.AppViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: AppViewModel by viewModels()
    var TYPE = 1
    private val countryIdMap = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Buttons click listener
        binding.indevidual.setOnClickListener {
            isIndividualSelected()

        }
        binding.commircer.setOnClickListener {
            isTraderSelected()
            replaceFragment(TraderSignUpFragment (viewModel))
        }
        if (savedInstanceState == null) {
            replaceFragment(IndividualSignUpFragment(viewModel))
        }




    }

    // To change the style when individual is selected and set the type
    private fun isIndividualSelected() {
        TYPE = 1
        binding.indevidual.setBackgroundColor(resources.getColor(R.color.white))
        binding.indevidual.setStrokeColorResource(R.color.orange)
        binding.indevidual.setTextColor(resources.getColor(R.color.blue))

        binding.commircer.setBackgroundColor(resources.getColor(R.color.blue))
        binding.commircer.setStrokeColorResource(R.color.gray)
        binding.commircer.setTextColor(resources.getColor(R.color.gray))
        //To get the fragment that has the individual sign up
        replaceFragment(IndividualSignUpFragment(viewModel))
    }

    // To change the style when trader is selected and set the type
    private fun isTraderSelected() {
        TYPE = 2
        binding.commircer.setBackgroundColor(resources.getColor(R.color.white))
        binding.commircer.setStrokeColorResource(R.color.orange)
        binding.commircer.setTextColor(resources.getColor(R.color.blue))

        binding.indevidual.setBackgroundColor(resources.getColor(R.color.blue))
        binding.indevidual.setStrokeColorResource(R.color.gray)
        binding.indevidual.setTextColor(resources.getColor(R.color.gray))

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
