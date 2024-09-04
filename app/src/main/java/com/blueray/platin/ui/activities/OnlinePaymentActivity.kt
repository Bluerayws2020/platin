package com.blueray.platin.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.blueray.platin.R
import com.blueray.platin.helpers.Msa
import com.blueray.platin.ui.fragments.CartFragment
import com.blueray.platin.ui.fragments.PaymentFragment
import com.oppwa.mobile.connect.checkout.meta.CheckoutActivityResult
import com.oppwa.mobile.connect.checkout.meta.CheckoutActivityResultContract
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.provider.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.json.JSONObject

class OnlinePaymentActivity : BasePaymentActivity() {

    //    protected var resourcePath: String? = null
    var chckoutIDRequest = ""
    var transactionState: TransactionState? = null

    private var ORDER_ID: String? = ""
    private var AMOUNT: String? = ""

    enum class TransactionState {
        NEW,
        PENDING,
        COMPLETED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_payment)

        ORDER_ID = intent.extras?.getString("orderId")
        AMOUNT = intent.extras?.getString("amount")

        if (savedInstanceState != null) {
            Log.e("***", "savedInstanceState")
            resourcePath = savedInstanceState.getString(PaymentFragment.STATE_RESOURCE_PATH)
        }

        getCheckoutDataVolley(this, 0) { checkoutId ->
            if (checkoutId != null) {
                // Successfully fetched checkoutId
                Log.e("***SUCCESS", "Fetched Checkout ID: $checkoutId")
                chckoutIDRequest = checkoutId
            } else {
                // Failed to fetch checkoutId
                Log.e("***FAILURE", "Failed to fetch Checkout ID")
            }
        }

        Handler().postDelayed({
            Msa.requestCheckoutId(

                "200",
                config.CURRENCY,
                config.PAYMENT_TYPE,
                config.SERVER_MODE,
                config.AUTHORIZATION,

                HashMap<String, String>().apply {
                    this["entityId"] = config.MERCHANT_ID
                }
            ) { checkoutId, _ -> onCheckoutIdReceived(chckoutIDRequest) }
            Log.e("***", chckoutIDRequest)

        }, 2000) // delay in milliseconds


    }

    override fun onCheckoutIdReceived(checkoutId: String?) {
        super.onCheckoutIdReceived(checkoutId)
        if (checkoutId != null) {
            openCheckoutUI(checkoutId)
        }
    }

    private fun openCheckoutUI(checkoutId: String) {
        val checkoutSettings = createCheckoutSettings(
            checkoutId,
            "Paradise",
            AMOUNT.toString(),
            config.CURRENCY,
            config.PAYMENT_BRANDS,
            config.MERCHANT_ID,
            config.PROVIDER_MODE
        )
        checkoutSettings.isCardScanningEnabled = true

        /* Start the checkout activity */
        checkoutLauncher.launch(checkoutSettings)
    }

    protected val checkoutLauncher = registerForActivityResult(
        CheckoutActivityResultContract()
    ) { result: CheckoutActivityResult ->
        this.handleCheckoutActivityResult(result)
    }

    fun handleCheckoutActivityResult(result: CheckoutActivityResult) {
        hideProgressBar()

        if (result.isCanceled) {
            return
        }

        if (result.isErrored) {
            val error: PaymentError? = result.paymentError
            error?.let {
                showAlertDialog(it.errorMessage)
                Log.e("***error", chckoutIDRequest)
            }

            return
        }

        /* Transaction completed */


        // viewModel.retriveCheckOut(oreder_ID.toString(), "4", binding.note.text.toString())

        resourcePath = result.resourcePath

        /* Check the transaction type */
        if (transaction != null) {
            if (transaction.transactionType === TransactionType.SYNC) {
                transactionState = TransactionState.COMPLETED;

                //viewModel.retriveCheckOut(oreder_ID.toString(), "4", binding.note.text.toString())

            } else {
                // onNewIntent() might be already invoked if activity was destroyed in background,
                // make sure you don't overwrite COMPLETED state

                if (!transactionState?.equals(TransactionState.COMPLETED)!!) {

                    showAlert("يرجى انتظار تاكيد الدفع والمحاولة بعد مدة ")
                    transactionState = TransactionState.PENDING;
                }
            }
        }
    }

    private fun hasCallBackScheme(intent: Intent): Boolean {
        return intent.scheme == getString(R.string.app_name) ||
                intent.scheme == getString(R.string.app_name) ||
                intent.scheme == getString(R.string.app_name)
    }

    @ExperimentalCoroutinesApi
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        /* Check of the intent contains the callback scheme */
        if (resourcePath != null && hasCallBackScheme(intent)) {
            requestPaymentStatus(resourcePath!!)
            transactionState = TransactionState.COMPLETED;

        }
    }

    fun getCheckoutDataVolley(context: Context, retryCount: Int, callback: (String?) -> Unit) {
        val url =
            " https://eu-prod.oppwa.com/v1/checkouts?entityId=8acda4ce8dcb359d018e0d68c71b2c3c"

        // Initialize a new RequestQueue instance
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        // Initialize a new StringRequest
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.d("RESSSSPONSE", response.toString())
                try {
                    // Parse the JSON response
                    // Parse the JSON response
                    val jsonResponse = JSONObject(response)
                    val checkoutId = jsonResponse.getString("id")
                    Log.d("CHECKOUT_ID", checkoutId ?: "No ID found")

                    // Invoke the callback function with the fetched ID
                    callback(checkoutId)
//                    getPayment(checkoutId)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("RESSSSPONSE2", response.toString())
                    Log.d("e", e.toString())

                }
            },
            Response.ErrorListener { error ->
                if (retryCount < 3) {  // Assuming you want to retry a maximum of 3 times
                    getCheckoutDataVolley(context, retryCount + 1, callback)
                } else {
                    callback(null)
                }
                when (error) {
                    is TimeoutError -> Log.d("Error", "Connection Timed Out")
                    is NoConnectionError -> Log.d("Error", "No Connection Error")
                    is AuthFailureError -> Log.d("Error", "Authentication Error")
                    is ServerError -> Log.d("Error", "Server Error")

                    is NetworkError -> Log.d("Error", "Network Error")
                    is ParseError -> Log.d("Error", "Parse Error")
                    else -> Log.d("Error", "Unknown Error")
                }
                callback(null)
            }


        ) {
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] =
                    "Bearer OGFjZGE0Y2U4ZGNiMzU5ZDAxOGUwZDY4NTQwODJjMzZ8V2I3Q2p0NWtkcGVIQTVCRA=="
                params["Content-Type"] = "application/x-www-form-urlencoded"



                return params
            }

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["amount"] = AMOUNT.toString()
                params["currency"] = "JOD"
                params["paymentType"] = "DB"

                params["merchantTransactionId"] = ORDER_ID.toString()
                params["customer.email"] = "m.jaber@bluerayws.com"  // Replace with user's email
                params["billing.street1"] = "ISTKLAL"  // Replace with actual street address
                params["billing.city"] = "Amman"  // Replace with actual city
                params["billing.state"] = "Mokaplen"  // Replace with actual state
                params["billing.country"] = "JO"  // Replace with country code (A2[A-Z]{2} format)
                params["billing.postcode"] = "1118"  // Replace with actual postcode
                params["customer.givenName"] = "Mohammad"  // Replace with user's first name
                params["customer.surname"] = "Jaber"  // Replace with user's last name

                Log.d("HEADERSparams", params.toString())

                return params
            }
        }

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest)
    }

}