package com.blueray.platin.helpers

import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.Connect.ProviderMode

data class Config(
    var CONNECTION_TIMEOUT:Long = 5000,

    var MERCHANT_ID:String = "8acda4ce8dcb359d018e0d68c71b2c3c",
    var SERVER_MODE: ServerMode = ServerMode.LIVE,
    var PROVIDER_MODE: ProviderMode = Connect.ProviderMode.LIVE,
    var LOG_TAG:String = "msdk.demo",
    var AUTHORIZATION:String= "OGFjZGE0Y2U4ZGNiMzU5ZDAxOGUwZDY4NTQwODJjMzZ8V2I3Q2p0NWtkcGVIQTVCRA==",

    /* The configuration values to change across the app */
    /* The default amount and currency */
    var CURRENCY:String = "JOD",
    var PAYMENT_TYPE:String = "DB",
    /* The payment brands for Ready-to-Use UI and Payment Button */
    var PAYMENT_BRANDS:Set<String> = linkedSetOf("VISA", "MASTER"),
    /* The default payment brand for payment button */
    var PAYMENT_BUTTON_BRAND:String = "VISA",

    /* The card info for SDK & Your Own UI */
//    var CARD_BRAND:String = "VISA",
//    var CARD_HOLDER_NAME:String = "JOHN DOE",
//    var CARD_NUMBER:String = "4440000009900010",
//    var CARD_EXPIRY_MONTH:String = "01",
//    var CARD_EXPIRY_YEAR:String = "39",
//    var CARD_CVV:String = "100"
)
enum class ServerMode {
    TEST, LIVE
}