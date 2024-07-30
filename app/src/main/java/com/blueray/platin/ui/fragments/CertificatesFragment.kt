package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.R
import com.blueray.platin.adapters.PicturesAdapter
import com.blueray.platin.adapters.onCategoryClick
import com.blueray.platin.databinding.FragmentCertificatesBinding
import com.blueray.platin.databinding.FragmentPicturesBinding
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class CertificatesFragment : BaseFragment<FragmentCertificatesBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCertificatesBinding {
        return FragmentCertificatesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding?.includeTab?.title?.text = "الشهادات"
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

    }
}