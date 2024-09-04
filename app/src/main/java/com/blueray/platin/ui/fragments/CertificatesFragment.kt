package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.platin.adapters.CertificatesAdapter
import com.blueray.platin.databinding.FragmentCertificatesBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class CertificatesFragment : BaseFragment<FragmentCertificatesBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private lateinit var adapter: CertificatesAdapter
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
        viewModel.retrieveCertifications()
        getCertifications()

    }

    private fun getCertifications() {
        viewModel.getCertifications().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    adapter = CertificatesAdapter(result.data.data)
                    binding?.certificatesRv?.adapter = adapter
                    binding?.certificatesRv?.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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