package com.example.go4lunchimproved


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_workmates_list.*


class WorkmatesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_workmates_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        val users: ArrayList<User> = ArrayList()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val uid: String? = document["uid"].toString()
                    val email: String? = document["email"].toString()
                    val name: String? = document["name"].toString()
                    val photourl: String? = document["photoUrl"].toString()
                    val user = User(uid, name, email, photourl)
                    users.add(user)

                }
                val adapter = UserAdapter(users)
                val linearLayoutManager = LinearLayoutManager(context)
                recycle_workmates.layoutManager = linearLayoutManager
                recycle_workmates.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)


            }


    }
}
