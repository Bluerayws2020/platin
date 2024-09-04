package com.blueray.platin.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.API.CartItemListener
import com.blueray.platin.R
import com.blueray.platin.adapters.CartItemsAdapter
import com.blueray.platin.databinding.FragmentCartBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding, AppViewModel>() {

    companion object {
        var couponCode = ""

    }

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private lateinit var cartItemsAdapter: CartItemsAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater, container, false)
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

        viewModel.retrieveMyCart()
        viewModel.retrieveMyProfile()
        getMyCart()
        getMyProfile()
        checkCoupon()
        deleteFromCart()
        updateCartQuantity()
        binding?.saveCartButton?.setOnClickListener {
            binding?.couponLayout?.visibility = View.VISIBLE
            lifecycleScope.launch {
                delay(200)
                binding?.nestedScrollLayout?.smoothScrollTo(0, binding?.couponLayout!!.top)
            }
            binding?.number2?.strokeColor = resources.getColor(R.color.orange)
            binding?.number2tv?.setTextColor(resources.getColor(R.color.orange))
            binding?.couponTv?.setTextColor(resources.getColor(R.color.orange))

        }
        binding?.noCoupon?.setOnClickListener {
            binding?.clintInfoLayout?.visibility = View.VISIBLE
            lifecycleScope.launch {
                delay(200)
                binding?.nestedScrollLayout?.smoothScrollTo(0, binding?.clintInfoLayout!!.top)
            }
            binding?.number3?.strokeColor = resources.getColor(R.color.orange)
            binding?.number3tv?.setTextColor(resources.getColor(R.color.orange))
            binding?.clientInfoTv?.setTextColor(resources.getColor(R.color.orange))
        }
//        binding?.priceBeforeCoupon?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding?.continueToPayment?.setOnClickListener {
            navController?.navigate(R.id.action_cartFragment_to_paymentFragment)
        }

        binding?.activateCoupon?.setOnClickListener {
            viewModel.retrieveCheckCoupon(binding?.couponEt?.text.toString())

            binding?.clintInfoLayout?.visibility = View.VISIBLE
            lifecycleScope.launch {
                delay(200)
                binding?.nestedScrollLayout?.smoothScrollTo(0, binding?.clintInfoLayout!!.top)
            }
            binding?.number3?.strokeColor = resources.getColor(R.color.orange)
            binding?.number3tv?.setTextColor(resources.getColor(R.color.orange))
            binding?.clientInfoTv?.setTextColor(resources.getColor(R.color.orange))
        }

    }

    private fun getMyCart() {
        viewModel.getMyCart().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.status == 200) {
                        cartItemsAdapter =
                            CartItemsAdapter(result.data.data.customer_cart.toMutableList(),
                                object : CartItemListener {
                                    override fun onRemoveClick(position: Int, id: String) {
                                        val inflater = LayoutInflater.from(context)
                                        val dialogView = inflater.inflate(R.layout.dialog_confirm_removal, null)

                                        val alertDialog = AlertDialog.Builder(context)
                                            .setView(dialogView)
                                            .create()

                                        // Access the buttons in the dialog layout and set their click listeners
                                        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
                                        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

                                        btnOk.setOnClickListener {
                                            // Perform the removal actions here
                                            viewModel.retrieveDeleteFromCart(id)
                                            cartItemsAdapter.removeItemAt(position)
                                            alertDialog.dismiss() // Close the dialog
                                        }

                                        btnCancel.setOnClickListener {
                                            alertDialog.dismiss() // Close the dialog without doing anything
                                        }

                                        // Show the custom dialog
                                        alertDialog.show()
                                    }



                                    override fun onChangeQuantityClick(
                                        id: String,
                                        quantity: String
                                    ) {
                                        viewModel.retrieveUpdateCartQuantity(
                                            id, quantity
                                        )
                                    }
                                }
                            )

                        binding?.recycler?.adapter = cartItemsAdapter
                        binding?.cartTotalPriceTv?.text =
                            result.data.data.endTotal.toString() + " JD"
                    } else {
                        binding?.cartLayout?.visibility = View.GONE
                        binding?.emptyCartTv?.visibility = View.VISIBLE
                    }

                }

                is NetworkResults.Error -> {
                    toast(result.exception.localizedMessage.toString())
                    Log.e("ERRORRCCS", result.exception.localizedMessage.toString())
                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }

    private fun getMyProfile() {
        viewModel.getMyProfile().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    binding?.name?.setText(result.data.data.first_name + " " + result.data.data.last_name)
                    binding?.phone?.setText(result.data.data.phone)
                    binding?.mail?.setText(result.data.data.email)
                    binding?.country?.setText(result.data.data.country_id.toString())
                    binding?.city?.setText(result.data.data.city_id.toString())
                }

                is NetworkResults.Error -> {

                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }

    private fun checkCoupon() {
        viewModel.getCheckCoupon().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                    if (result.data.status == 200) {
                        couponCode = binding?.couponEt?.text.toString()
                    }
                }

                is NetworkResults.Error -> {

                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }

    private fun deleteFromCart() {
        viewModel.getDeleteFromCart().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                    if (cartItemsAdapter.list.size == 0) {
                        viewModel.retrieveMyCart()

                    }
                }

                is NetworkResults.Error -> {

                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }

    private fun updateCartQuantity() {
        viewModel.getUpdateCartQuantity().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    viewModel.retrieveMyCart()
                    toast(result.data.message)
                }

                is NetworkResults.Error -> {
                    toast(result.exception.localizedMessage.toString())
                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }
}