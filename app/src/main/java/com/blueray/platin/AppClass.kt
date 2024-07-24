package com.blueray.platin

import android.app.Application
import android.content.Context

class AppClass : Application() {

    companion object {
        lateinit var context: Context

        }
    override fun onCreate() {
        super.onCreate()

        context =this

//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(ONESIGNAL_APP_ID)
//
//        OneSignal.promptForPushNotifications();
//        OneSignal.promptLocation()
    }
}