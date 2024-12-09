package com.app.platemate.model

data class FoodListing (
    val id: String = "",
    val foodName: String = "",
    val distance: String = "",
    val contactInfo: String = "",
    val postedTime: String = "",
    val imageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)