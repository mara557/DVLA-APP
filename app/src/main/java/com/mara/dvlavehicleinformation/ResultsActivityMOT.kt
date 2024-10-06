package com.mara.dvlavehicleinformation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mara.dvlavehicleinformation.adapters.MotTestsAdapter
import com.mara.dvlavehicleinformation.models.MotTest
import com.mara.dvlavehicleinformation.models.VehicleComment
import org.json.JSONArray
import org.json.JSONException

class ResultsActivityMOT : AppCompatActivity() {

    private lateinit var loadingProgressBar: View
    private lateinit var errorMessageTextView: TextView
    private lateinit var registrationTextView: TextView
    private lateinit var makeModelTextView: TextView
    private lateinit var fuelTypeTextView: TextView
    private lateinit var motTestsRecyclerView: RecyclerView
    private lateinit var refreshFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_mot)

        // Initialize views
        loadingProgressBar = findViewById(R.id.loadingProgressBarMOT)
        errorMessageTextView = findViewById(R.id.errorMessageTextViewMOT)
        registrationTextView = findViewById(R.id.registrationTextView)
        makeModelTextView = findViewById(R.id.makeModelTextView)
        fuelTypeTextView = findViewById(R.id.fuelTypeTextView)
        motTestsRecyclerView = findViewById(R.id.motTestsRecyclerView)
        refreshFab = findViewById(R.id.refreshFab)

        // Setup RecyclerView
        motTestsRecyclerView.layoutManager = LinearLayoutManager(this)
        motTestsRecyclerView.adapter = MotTestsAdapter(emptyList())

        val apiURL = intent.getStringExtra("apiURL")
        Log.d("API_REQUEST", "API URL from Intent: $apiURL")

        apiURL?.let {
            fetchDataFromAPI(it)
        }

        // Refresh Button Click Listener
        refreshFab.setOnClickListener {
            apiURL?.let {
                fetchDataFromAPI(it)
            }
        }
    }

    private fun fetchDataFromAPI(apiURL: String) {
        showLoading()

        val apiRequestTask = VehicleInformationApiTask(this, apiURL) { response ->
            runOnUiThread {
                hideLoading()
                handleApiResponse(response)
            }
        }
        apiRequestTask.execute()
    }

    private fun handleApiResponse(response: String) {
        if (response.isBlank()) {
            displayErrorMessage("Empty or null API response")
            return
        }

        try {
            val jsonArray = JSONArray(response)
            if (jsonArray.length() == 0) {
                displayErrorMessage(getString(R.string.no_mot_tests_found))
                return
            }

            val motTestsList = mutableListOf<MotTest>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val registration = jsonObject.optString("registration")
                registrationTextView.text = "Registration: $registration"

                val makeModel = "${jsonObject.optString("make")} ${jsonObject.optString("model")}"
                makeModelTextView.text = "Make & Model: $makeModel"

                val fuelType = jsonObject.optString("fuelType")
                fuelTypeTextView.text = "Fuel Type: $fuelType"

                val motTestsArray = jsonObject.optJSONArray("motTests")
                motTestsArray?.let {
                    for (j in 0 until it.length()) {
                        val motTestObject = it.getJSONObject(j)
                        val commentsList = mutableListOf<VehicleComment>()

                        val rfrAndCommentsArray = motTestObject.optJSONArray("rfrAndComments")
                        rfrAndCommentsArray?.let { rfrArray ->
                            for (k in 0 until rfrArray.length()) {
                                val commentObject = rfrArray.getJSONObject(k)
                                val comment = VehicleComment(
                                    text = commentObject.optString("text"),
                                    type = commentObject.optString("type"),
                                    dangerous = commentObject.optBoolean("dangerous")
                                )
                                commentsList.add(comment)
                            }
                        }

                        val motTest = MotTest(
                            completedDate = motTestObject.optString("completedDate"),
                            testResult = motTestObject.optString("testResult"),
                            testNumber = motTestObject.optString("motTestNumber"),
                            odometerValue = motTestObject.optString("odometerValue"),
                            odometerUnit = motTestObject.optString("odometerUnit"),
                            expiryDate = motTestObject.optString("expiryDate"),
                            odometerResultType = motTestObject.optString("odometerResultType"),
                            comments = commentsList // Associate comments
                        )
                        motTestsList.add(motTest)
                    }
                }
            }

            // Update Adapter
            (motTestsRecyclerView.adapter as MotTestsAdapter).updateData(motTestsList)

        } catch (e: JSONException) {
            e.printStackTrace()
            displayErrorMessage("Error parsing JSON: ${e.message}")
            Log.e("JSON_PARSE_ERROR", "Error parsing JSON: ${e.message}")
        }
    }

    private fun displayErrorMessage(message: String) {
        errorMessageTextView.text = message
        errorMessageTextView.visibility = View.VISIBLE
        Snackbar.make(findViewById(R.id.rootLayoutMOT), message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
        errorMessageTextView.visibility = View.GONE
    }

    private fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
    }
}
