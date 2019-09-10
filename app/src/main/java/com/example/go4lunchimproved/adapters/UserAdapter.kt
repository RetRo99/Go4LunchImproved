package com.example.go4lunchimproved.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.model.User
import com.example.go4lunchimproved.utils.inflate
import com.example.go4lunchimproved.utils.loadProfilePhoto
import kotlinx.android.synthetic.main.fragment_list_workmates_view_item.view.*

class UserAdapter(private var users: List<User>, val resturantView: Boolean = false) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {
    override fun getItemCount(): Int {
        return users.size
    }

    fun update(modelList: List<User>) {
        users = modelList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindUser(users[position], resturantView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflatedView = parent.inflate(R.layout.fragment_list_workmates_view_item, false)
        return UserHolder(inflatedView)
    }


    class UserHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bindUser(user: User, resturantView: Boolean) {
            view.fragment_list_workmates_view_item_workmate_photo.loadProfilePhoto(user.photoUrl)
            if (!resturantView) {
                view.fragment_list_workmates_view_item_workmate_details.text =
                    user.getUserRestaurant()
            } else {
                val description = "${user.name?.substringBefore(" ")} is joining!"
                view.fragment_list_workmates_view_item_workmate_details.text = description
            }

        }

    }
}