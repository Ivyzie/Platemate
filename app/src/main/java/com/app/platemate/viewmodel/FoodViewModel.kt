package com.app.platemate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.platemate.model.FoodListing
import com.app.platemate.model.FoodRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import com.google.android.gms.maps.model.LatLng

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _foodListings = MutableStateFlow<List<FoodListing>>(emptyList())
    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val foodListings: StateFlow<List<FoodListing>> = _foodListings
    val userLocation: StateFlow<LatLng?> = _userLocation

    init {
        fetchFoodListings()
    }

    fun updateUserLocation(location: LatLng) {
        viewModelScope.launch {
            _userLocation.value = location
        }
    }

    private fun fetchFoodListings() {
        viewModelScope.launch {
            _foodListings.value = repository.getFoodListings()
        }
    }

    fun addFoodListing(foodListing: FoodListing) {
        viewModelScope.launch {
            repository.addFoodListing(foodListing)
            fetchFoodListings() // Refresh the list after adding
        }
    }

    fun updateFoodListing(id: String, foodListing: FoodListing) {
        viewModelScope.launch {
            repository.updateFoodListing(id, foodListing)
            fetchFoodListings() // Refresh the list after updating
        }
    }
    
    fun deleteFoodListing(id: String) {
        viewModelScope.launch {
            repository.deleteFoodListing(id)
            fetchFoodListings() // Refresh the list after deleting
        }
    }
}

class FoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}