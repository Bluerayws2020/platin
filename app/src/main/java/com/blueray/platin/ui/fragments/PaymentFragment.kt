package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.databinding.FragmentPaymentBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.ui.activities.OnlinePaymentActivity
import com.blueray.platin.viewModels.AppViewModel

class PaymentFragment : BaseFragment<FragmentPaymentBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private var ORDER_ID = "0"
    private var AMOUNT = ""
    companion object{
        val TOTTalPrice = "TOTTalPrice"
        val orderID = "orderID"
        const val STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH"
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentBinding {
        return FragmentPaymentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding?.includeTab?.title?.text = "مشترياتي"
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

        binding?.confirmPayment?.setOnClickListener {
            binding?.finishPaymentLayout?.visibility = View.VISIBLE
            val intent = Intent(requireContext(), OnlinePaymentActivity::class.java);
            val bundle = Bundle()
            bundle.putString("orderId", ORDER_ID)
            bundle.putString("amount", AMOUNT)
            intent.putExtras(bundle)
            requireContext().startActivity(intent)
        }
        binding?.checkoutButton?.setOnClickListener {
            viewModel.retrieveCheckout(CartFragment.couponCode)
        }

        checkout()
    }

    private fun checkout() {
        viewModel.getCheckout().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                }

                is NetworkResults.Error -> {

                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }
}