package com.mara.dvlavehicleinformation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mara.dvlavehicleinformation.R
import com.mara.dvlavehicleinformation.models.MotTest

class MotTestsAdapter(private var motTests: List<MotTest>) :
    RecyclerView.Adapter<MotTestsAdapter.MotTestViewHolder>() {

    class MotTestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val completedDate: TextView = itemView.findViewById(R.id.completedDateTextView)
        val testResult: TextView = itemView.findViewById(R.id.testResultTextView)
        val testNumber: TextView = itemView.findViewById(R.id.testNumberTextView)
        val expiryDate: TextView = itemView.findViewById(R.id.expiryDateTextView)
        val odometer: TextView = itemView.findViewById(R.id.odometerTextView)
        val commentsRecyclerView: RecyclerView = itemView.findViewById(R.id.commentsRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotTestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mot_test, parent, false)
        return MotTestViewHolder(view)
    }

    override fun onBindViewHolder(holder: MotTestViewHolder, position: Int) {
        val motTest = motTests[position]
        holder.completedDate.text = "Completed Date: ${motTest.completedDate}"
        holder.testResult.text = "Test Result: ${motTest.testResult}"
        holder.testNumber.text = "Test Number: ${motTest.testNumber}"
        holder.expiryDate.text = "Expiry Date: ${motTest.expiryDate}"
        holder.odometer.text = "Odometer: ${motTest.odometerValue} ${motTest.odometerUnit}"

        // Setup Comments RecyclerView
        holder.commentsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.commentsRecyclerView.adapter = CommentsAdapter(motTest.comments)
    }

    override fun getItemCount(): Int = motTests.size

    // Method to update data
    fun updateData(newMotTests: List<MotTest>) {
        motTests = newMotTests
        notifyDataSetChanged()
    }
}
