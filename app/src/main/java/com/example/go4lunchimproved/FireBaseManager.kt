package com.example.go4lunchimproved

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

object FireBaseManager {

    init {
        getUsers()
    }

    private var currentUser: MutableLiveData<User> = MutableLiveData()

    fun getCurrentUser(): MutableLiveData<User> {
        return currentUser

    }

    private val allUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getAllUsers(): MutableLiveData<List<User>> {
        return allUsers
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
                    currentUser.postValue(result.toObject(User::class.java)!!)
                }


            }
    }

    private fun getUsers() {
        val db = FirebaseFirestore.getInstance()

        val users: ArrayList<User> = ArrayList()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val user = document.toObject(User::class.java)
                    users.add(user)
                }
                allUsers.postValue(users)

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)


            }
    }

    fun getFireBaseUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }


}