package com.mara.dvlavehicleinformation

import ApiRequestTask
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ResultsActivity : AppCompatActivity() {

    private lateinit var loadingProgressBar: View
    private lateinit var errorMessageTextView: TextView
    private lateinit var dynamicLayout: LinearLayout
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        // Initialize views
        rootView = findViewById(R.id.rootLayout)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        errorMessageTextView = findViewById(R.id.errorMessageTextView)
        dynamicLayout = findViewById(R.id.dynamicLayout)

        val registrationNumber = intent.getStringExtra("registrationNumber")

        if (!registrationNumber.isNullOrBlank()) {
            fetchVehicleInformation(registrationNumber)
        } else {
            displayErrorMessage("Invalid registration number")
        }
    }

    private fun fetchVehicleInformation(registrationNumber: String) {
        showLoading(true)
        ApiRequestTask(this, registrationNumber) { result ->
            runOnUiThread {
                showLoading(false)
                handleApiResponse(result)
            }
        }.execute()
    }

    private fun handleApiResponse(result: String?) {
        if (result.isNullOrBlank()) {
            displayErrorMessage("Empty or null API response")
            return
        }

        try {
            val json = JSONObject(result)
            val errors = json.optJSONArray("errors")

            if (errors != null && errors.length() > 0) {
                val errorMessage = errors.getJSONObject(0).getString("detail")
                displayErrorMessage(errorMessage)
                return
            }

            dynamicLayout.removeAllViews() // Clear the layout before adding new views

            // Map for title-case keys and properly formatted labels
            val formattedLabels = mapOf(
                "registrationNumber" to "Registration Number",
                "taxStatus" to "Tax Status",
                "taxDueDate" to "Tax Due Date",
                "motStatus" to "MOT Status",
                "make" to "Make",
                "yearOfManufacture" to "Year of Manufacture",
                "engineCapacity" to "Engine Capacity",
                "co2Emissions" to "CO2 Emissions",
                "fuelType" to "Fuel Type",
                "markedForExport" to "Marked for Export",
                "colour" to "Colour",
                "typeApproval" to "Type Approval",
                "revenueWeight" to "Revenue Weight",
                "euroStatus" to "Euro Status",
                "dateOfLastV5CIssued" to "Date of Last V5C Issued",
                "wheelplan" to "Wheelplan",
                "monthOfFirstRegistration" to "Month of First Registration"
            )

            // Loop through each key in the JSON object and create views
            json.keys().forEach { key ->
                val value = json.optString(key, "N/A")

                // Use formattedLabels map for consistent, readable labels
                val formattedKey = formattedLabels[key] ?: key.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }

                // Create a CardView to hold each info row
                val cardView = MaterialCardView(this).apply {
                    radius = 8f
                    elevation = 4f
                    setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 8, 0, 8)
                    }
                }

                // Create a horizontal LinearLayout inside the CardView
                val linearLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(16)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }

                // Create a TextView for the key (label)
                val keyTextView = TextView(this).apply {
                    text = "$formattedKey:"
                    textSize = 16f
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0.4f
                    ).apply {
                        gravity = Gravity.START
                    }
                }

                // Create a TextView for the value
                val valueTextView = TextView(this).apply {
                    text = value
                    textSize = 16f
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                    gravity = Gravity.END
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0.6f
                    ).apply {
                        gravity = Gravity.END
                    }
                }

                // Add TextViews to the LinearLayout
                linearLayout.addView(keyTextView)
                linearLayout.addView(valueTextView)

                // Add LinearLayout to the CardView
                cardView.addView(linearLayout)

                // Add the CardView to the dynamic layout
                dynamicLayout.addView(cardView)
            }
        } catch (e: JSONException) {
            displayErrorMessage("Registration number not found or INVALID")
            e.printStackTrace()
        }
    }

    private fun displayErrorMessage(message: String) {
        errorMessageTextView.text = message
        errorMessageTextView.visibility = View.VISIBLE
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        dynamicLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        errorMessageTextView.visibility = View.GONE
    }
}
