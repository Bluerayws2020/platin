package com.blueray.platin.ui.activities

import android.app.AlertDialog
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.blueray.platin.R

abstract class BaseActivity : AppCompatActivity(){
    protected var progressBar: ProgressBar? = null

    protected fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    protected fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    protected fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(R.string.app_name, null)
            .setCancelable(false)
            .show()
    }

    protected fun showAlertDialog(message: String) {
        showAlert(message)
    }

    protected fun showAlertDialog(messageId: Int) {
        showAlert(getString(messageId))
    }


}