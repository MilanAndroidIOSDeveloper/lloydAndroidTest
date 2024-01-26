package com.androidtest.bymilanchothani.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.androidtest.bymilanchothani.R

class Utility {
    companion object {
        fun showNoInternetDialog(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setView(LayoutInflater.from(context).inflate(R.layout.no_internet_dialogue, null))
            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            val dismissButton = dialog.findViewById<Button>(R.id.exit_button)
            dismissButton!!.setOnClickListener {
                dialog.dismiss()

            }
        }
    }

    object NetworkUtils {
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }
    }

    object ToastUtils {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}


