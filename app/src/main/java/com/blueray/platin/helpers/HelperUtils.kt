package com.blueray.platin.helpers

import android.content.Context
import android.content.Intent

object HelperUtils {

    const val SHARED_PREF = "PARADISE_KEY"

    fun getLang(mContext: Context?): String {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString("lang", "ar")!!
    }

    fun getUID(mContext: Context?): String {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString("uid", "0")!!
    }

    fun getToken(mContext: Context?): String {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString("token", "0")!!
    }

    fun isGuest(mContext: Context?): Boolean {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val isUserIDEmpty = sharedPreferences?.getString("uid", "0") == "0"
        if (isUserIDEmpty)
            openLoginActivity(mContext)
        return isUserIDEmpty
    }

    fun openLoginActivity(mContext: Context?) {
       // Toast.makeText(mContext, mContext?.getString(R.string.guest_login), Toast.LENGTH_LONG).show()
//        val splashIntent = Intent(mContext, StartUpActivity::class.java)
//        mContext?.startActivity(splashIntent)
    }
}