package com.example.go4lunchimproved

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FireStoreManager {

    private lateinit var currentUser: User

    fun saveOrSetCurrentUser() {
        val db = FirebaseFirestore.getInstance()

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        currentUser =
            User(
                firebaseUser?.uid,
                firebaseUser?.displayName,
                firebaseUser?.email,
                firebaseUser?.photoUrl.toString()
            )
        db.collection("users").document(currentUser.uid!!)
            .get()
            .addOnSuccessListener { result ->
                if (!result.exists()) {
                    db.collection("users").document(currentUser.uid.toString())
                        .set(currentUser)
            }


                                                     

    } }

    fun getCurrentUser(): User {
        return currentUser

    }

}