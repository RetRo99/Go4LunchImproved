package com.example.go4lunchimproved.network

import androidx.lifecycle.MutableLiveData
import com.example.go4lunchimproved.model.User
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

    private val allUsers: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun getAllUsers(): MutableLiveData<ArrayList<User>> {
        return allUsers
    }

    val visitedRestaurants: ArrayList<String> = ArrayList()


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
                allUsers.postValue(users)

            }
        }
    }

    fun getFireBaseUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun updateUser(id:String?) {
        val db = FirebaseFirestore.getInstance()

        val userRef = db.collection("users").document(currentUser.value?.uid!!)

// Set the "isCapital" field of the city 'DC'
        userRef
            .update("name", "jst sm kekec")
    }


}