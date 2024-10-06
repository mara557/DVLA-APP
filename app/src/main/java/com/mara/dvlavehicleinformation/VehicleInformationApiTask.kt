package com.mara.dvlavehicleinformation

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.mara.dvlavehicleinformation.R
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response  // Correct import
import java.io.IOException

class VehicleInformationApiTask(
    private val context: Context,
    private val apiUrl: String,
    private val callback: (String) -> Unit
) : AsyncTask<Void, Void, String>() {

    private lateinit var progressBar: ProgressBar
    private val apiKey: String = ApiKeyProvider.getApiKeyMOT(context)

    override fun onPreExecute() {
        super.onPreExecute()
        // Show loading indicator
        progressBar = (context as AppCompatActivity).findViewById(R.id.loadingProgressBarMOT)
        progressBar.visibility = View.VISIBLE
    }

    override fun doInBackground(vararg params: Void?): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(apiUrl)
            .header("Accept", "application/json+v6")
            .header("x-api-key", apiKey)
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body()?.string() ?: ""
            } else {
                "Error ${response.code()}: ${response.message()}"  // Proper usage
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }

    override fun onPostExecute(result: String) {
        // Hide loading indicator
        progressBar.visibility = View.GONE
        Log.d("VEHICLE_API_RESPONSE", result)
        callback(result)
    }
}
