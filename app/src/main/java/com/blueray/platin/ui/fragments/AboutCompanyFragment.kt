package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.databinding.FragmentAboutCompanyBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class AboutCompanyFragment : BaseFragment<FragmentAboutCompanyBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutCompanyBinding {
        return FragmentAboutCompanyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding?.includeTab?.title?.text = "عن الشركة"
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

        viewModel.retrieveAboutUs()
        getAboutUs()


    }

    private fun getAboutUs() {
        viewModel.getAboutUs().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    binding?.titleTv?.text = result.data.data.aboutTitle
                    binding?.aboutDescriptionTv?.text = result.data.data.aboutDescription
                    binding?.ourHistoryTitle?.text = result.data.data.ourHistoryTitle
                    binding?.ourHistoryDescription?.text = result.data.data.ourHistoryDescription
                    binding?.distinguishUsTitle?.text = result.data.data.distinguishUsTitle
                    binding?.distinguishUsDescription?.text =
                        result.data.data.distinguishUsDescription
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }
}