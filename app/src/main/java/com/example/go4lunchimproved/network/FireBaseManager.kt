package com.example.go4lunchimproved.network

import androidx.lifecycle.MutableLiveData
import com.example.go4lunchimproved.model.User
import com.example.go4lunchimproved.model.Venue
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

    fun getPickedRestaurant():String? {
        return currentUser.value?.restaurantId

    }

   private fun getRestaurantText():String? {
        return currentUser.value?.name

    }

    private val mAllUsers: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun getAllUsers(): MutableLiveData<ArrayList<User>> {
        return allUsers
    }

    private var allUsers: MutableLiveData<ArrayList<User>> = MutableLiveData()

    val visitedRestaurants: ArrayList<String> = ArrayList()


    fun filterUsers(criteria: String = "") {
        if (criteria.isEmpty()) allUsers.postValue(mAllUsers.value)
        else {
            val matchingList = ArrayList<User>()
            if (mAllUsers.value != null) {
                for (user in mAllUsers.value!!) {
                    if (user.matchesCriteria(criteria)) matchingList.add(user)
                }

            }
            allUsers.value= matchingList

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
                    currentUser.postValue(result.toObject(
                        User::class.java)!!)

                }


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
                mAllUsers.postValue(users)
                allUsers.postValue(users)

            }
        }
    }

    fun getFireBaseUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun updateUser(restaurant: Venue?, deletePicked:Boolean = false) {
        val db = FirebaseFirestore.getInstance()
        val firstName = currentUser.value?.name?.substringBefore(" ")

        if(!deletePicked){
            val restaurantType = restaurant?.categories?.get(0)?.name
            val restaurantName = restaurant?.name
            val pickedRestaurantText = "$firstName is eating $restaurantType ($restaurantName)"


            db.collection("users").document(currentUser.value?.uid!!).update("restaurantId", restaurant?.id)
            db.collection("users").document(currentUser.value?.uid!!).update("pickedRestaurantText", pickedRestaurantText)

            currentUser.value!!.restaurantId = restaurant?.id
            currentUser.value!!.pickedRestaurantText = pickedRestaurantText

        }else{
            db.collection("users").document(currentUser.value?.uid!!).update("restaurantId","")
            db.collection("users").document(currentUser.value?.uid!!).update("pickedRestaurantText", "")
            currentUser.value!!.restaurantId = ""
            currentUser.value!!.pickedRestaurantText = ""



        }





    }


}