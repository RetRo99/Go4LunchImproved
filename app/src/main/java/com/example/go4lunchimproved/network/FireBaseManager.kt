@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.go4lunchimproved.network

import androidx.lifecycle.MutableLiveData
import com.example.go4lunchimproved.model.FireStoreRestaurant
import com.example.go4lunchimproved.model.User
import com.example.go4lunchimproved.network.Repository.loadSquareNearbyRestaurants
import com.example.go4lunchimproved.utils.getCurrentDate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

object FireBaseManager {

    init {
        getRestaurants()
        getUsers()
    }

    private var currentUser: MutableLiveData<User> = MutableLiveData()

    fun getCurrentUser(): MutableLiveData<User> {
        return currentUser

    }

    fun getPickedRestaurant(): String? {
        return currentUser.value?.restaurantId

    }

    private fun getRestaurantText(): String? {
        return currentUser.value?.name

    }

    private var mAllUsers: ArrayList<User> = ArrayList()

    fun getAllUsers(): MutableLiveData<ArrayList<User>> {
        return allUsers
    }

    private var allUsers: MutableLiveData<ArrayList<User>> = MutableLiveData()

    val visitedRestaurants: ArrayList<String> = ArrayList()


    fun filterUsers(criteria: String = "") {
        if (criteria.isEmpty()) allUsers.postValue(mAllUsers)
        else {
            val matchingList = ArrayList<User>()
            for (user in mAllUsers) {
                if (user.matchesCriteria(criteria)) matchingList.add(user)
            }
            allUsers.value = matchingList

        }


    }




    private fun getRestaurants() {
        val db = FirebaseFirestore.getInstance()

        db.collection("restaurants")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    visitedRestaurants.add(document.id)
                }


            }
    }

    fun setFireStoreUser() {

        val db = FirebaseFirestore.getInstance()

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val user =
            User(
                firebaseUser?.uid,
                firebaseUser?.displayName,
                firebaseUser?.email,
                firebaseUser?.photoUrl.toString()
            )
        db.collection("users").document(user.uid!!)
            .get()
            .addOnSuccessListener { result ->
                if (!result.exists()) {
                    db.collection("users").document(user.uid.toString())
                        .set(user)

                } else {
                    currentUser.postValue(
                        result.toObject(
                            User::class.java
                        )!!
                    )

                }


            }
    }

    fun loadRestaurants(locationString: String) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("savedRestaurants").document(getCurrentDate()).collection("list")
        val restaurants:ArrayList<FireStoreRestaurant> = ArrayList()
        ref.get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    loadSquareNearbyRestaurants(locationString, null)
                }  else{
                    for (item in documents) {
                        val restaurant =  item.toObject(FireStoreRestaurant::class.java)
                        restaurants.add(restaurant)
                    }
                    loadSquareNearbyRestaurants(locationString, restaurants)

                }

            }


    }
    fun saveRestaurants(listOfRestaurant: ArrayList<FireStoreRestaurant>) {

        val db = FirebaseFirestore.getInstance()

       val ref = db.collection("savedRestaurants").document(getCurrentDate()).collection("list")
        for (item in listOfRestaurant) {
            ref.add(item)
        }
        

    }




private fun getUsers() {
    val db = FirebaseFirestore.getInstance()

    val users: ArrayList<User> = ArrayList()

    val docRef = db.collection("users")
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            return@addSnapshotListener
        }
        if (snapshot != null) {
            users.clear()
            for (document in snapshot) {
                val user = document.toObject(User::class.java)
                users.add(user)

            }
            mAllUsers = users
            allUsers.postValue(users)

        }
    }
}

fun getFireBaseUser(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

fun updateUser(restaurant: FireStoreRestaurant?, deletePicked: Boolean = false) {
    val db = FirebaseFirestore.getInstance()
    val firstName = currentUser.value?.name?.substringBefore(" ")

    if (!deletePicked) {
        val restaurantType = restaurant?.type
        val restaurantName = restaurant?.restaurantName
        val pickedRestaurantText = "$firstName is eating $restaurantType ($restaurantName)"


        db.collection("users").document(currentUser.value?.uid!!)
            .update("restaurantId", restaurant?.restaurantID)
        db.collection("users").document(currentUser.value?.uid!!)
            .update("pickedRestaurantText", pickedRestaurantText)
        db.collection("users").document(currentUser.value?.uid!!)
            .update("pickedDate", getCurrentDate())

        currentUser.value!!.restaurantId = restaurant?.restaurantID
        currentUser.value!!.pickedRestaurantText = pickedRestaurantText

    } else {
        db.collection("users").document(currentUser.value?.uid!!).update("restaurantId", "")
        db.collection("users").document(currentUser.value?.uid!!).update("pickedRestaurantText", "")
        db.collection("users").document(currentUser.value?.uid!!).update("pickedDate", "")
        currentUser.value!!.restaurantId = ""
        currentUser.value!!.pickedRestaurantText = ""


    }


}


}