package com.example.go4lunchimproved

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_workmates_view_item.view.*

class UserAdapter(private var users:ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserHolder>() {
    override fun getItemCount(): Int {
return users.size    }

    fun update(modelList:ArrayList<User>){
        users = modelList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindUser(users[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflatedView = parent.inflate(R.layout.fragment_list_workmates_view_item, false)
        return UserHolder(inflatedView)
    }


    class UserHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

         fun bindUser(user: User) {
            view.apply {
                fragment_list_workmates_view_item_workmate_photo.loadProfilePhoto(user.photoUrl)
                fragment_list_workmates_view_item_workmate_details.text = user.name
            }
        }

    }
}