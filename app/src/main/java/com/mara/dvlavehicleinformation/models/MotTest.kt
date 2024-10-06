package com.mara.dvlavehicleinformation.models

data class MotTest(
    val completedDate: String,
    val testResult: String,
    val testNumber: String,
    val odometerValue: String,
    val odometerUnit: String,
    val expiryDate: String,
    val odometerResultType: String,
    val comments: List<VehicleComment> // New Property
)
