package com.example.go4lunchimproved.adapters

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.model.FireStoreRestaurant
import com.example.go4lunchimproved.ui.RestaurantDetail
import com.example.go4lunchimproved.utils.inflate
import com.example.go4lunchimproved.utils.loadPhotoFromUrl
import kotlinx.android.synthetic.main.restaurant_recycle_item.view.*

class RestaurantAdapter(val restaurants: List<FireStoreRestaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {


    class RestaurantHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bindRestaurant(restaurant: FireStoreRestaurant) {
            view.apply {
                fragment_list_restaurant_view_item_name.text = restaurant.restaurantName

                fragment_list_restaurant_view_item_address.text = restaurant.addresstext
                fragment_list_restaurant_view_item_distance.text = restaurant.restaurantDistance
                fragment_list_restaurant_view_item_opening_hours.text = restaurant.openingHours
                setOnClickListener {
                    val detailIntent = Intent(context, RestaurantDetail::class.java)
                    detailIntent.putExtra("venue", restaurant)
                    context.startActivity(detailIntent)

                }



                fragment_list_restaurant_view_item_image.loadPhotoFromUrl(restaurant.photoUrl)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val inflatedView = parent.inflate(R.layout.restaurant_recycle_item, false)
        return RestaurantHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        val venue = restaurants[position]
        holder.bindRestaurant(venue)

    }
}