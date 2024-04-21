package com.mara.dvlavehicleinformation

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class AskMidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_mid)

        // Find the WebView
        val webView = findViewById<WebView>(R.id.webViewAskMid)

        // Enable JavaScript
        webView.settings.javaScriptEnabled = true

        // Load the AskMID webpage with the new URL
        webView.loadUrl("https://ownvehicle.askmid.com/")

        // Log a message to verify if the activity is being created
        Log.d("AskMidActivity", "Activity created successfully")
    }
}