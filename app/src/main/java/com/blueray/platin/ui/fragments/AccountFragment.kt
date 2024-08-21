package com.blueray.platin.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.R
import com.blueray.platin.databinding.FragmentAccountBinding
import com.blueray.platin.helpers.HelperUtils
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.LoginActivity
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class AccountFragment : BaseFragment<FragmentAccountBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding?.includeTab?.title?.text = "حسابي"
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

        binding?.cart?.setOnClickListener {
            navController?.navigate(R.id.cartFragment)
        }

        binding?.logOut?.setOnClickListener {
            viewModel.retrieveLogout()
        }
        //Api calls
        viewModel.retrieveMyProfile()

        //Data observe
        getMyProfile()
        getLogout()
    }

    //Observers
    private fun getMyProfile() {
        viewModel.getMyProfile().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    binding?.firstName?.setText(result.data.data.first_name)
                    binding?.lastName?.setText(result.data.data.last_name)
                    binding?.phone?.setText(result.data.data.phone)
                    binding?.mail?.setText(result.data.data.email)
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }

    private fun getLogout() {
        viewModel.getLogout().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                    val sharedPreferences =
                        requireContext().getSharedPreferences(
                            HelperUtils.SHARED_PREF,
                            Context.MODE_PRIVATE
                        )
                    sharedPreferences.edit().apply {
                        putString("uid", "0")
                        putString("token", "0")
                        putString("userName", "")

                    }.apply()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }
}