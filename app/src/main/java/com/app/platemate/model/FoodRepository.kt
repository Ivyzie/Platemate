package com.app.platemate.model
import com.app.platemate.model.FoodListing
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FoodRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getFoodListings(): List<FoodListing> {
        return try {
            db.collection("foodListings")
                .get()
                .await()
                .map { document ->
                    document.toObject(FoodListing::class.java).copy(id = document.id)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addFoodListing(foodListing: FoodListing) {
        db.collection("foodListings").add(foodListing).await()
    }

    suspend fun updateFoodListing(id: String, foodListing: FoodListing) {
        db.collection("foodListings").document(id).set(foodListing).await()
    }
    
    suspend fun deleteFoodListing(id: String) {
        db.collection("foodListings").document(id).delete().await()
    }
}   