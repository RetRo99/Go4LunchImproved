package com.example.go4lunchimproved.adapters

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.utils.inflate
import com.example.go4lunchimproved.utils.loadPhotoFromUrl
import com.example.go4lunchimproved.model.Venue
import com.example.go4lunchimproved.ui.RestaurantDetail
import kotlinx.android.synthetic.main.restaurant_recycle_item.view.*

class SquareRestaurantAdapter(val restaurants: List<Venue>) :
    RecyclerView.Adapter<SquareRestaurantAdapter.SquareRestaurantHolder>() {


    class SquareRestaurantHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bindRestaurant(restaurant: Venue) {
            view.apply {
                fragment_list_restaurant_view_item_name.text = restaurant.name

                fragment_list_restaurant_view_item_address.text = restaurant.getAddressText()
                fragment_list_restaurant_view_item_distance.text = restaurant.getDistanceText()
                fragment_list_restaurant_view_item_opening_hours.text = restaurant.getOpeningHours()
                setOnClickListener {
                    val detailIntent = Intent(context, RestaurantDetail::class.java)
                    detailIntent.putExtra("venue", restaurant)
                    context.startActivity(detailIntent)

                }



                fragment_list_restaurant_view_item_image.loadPhotoFromUrl(restaurant.getPhotoUrl())
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareRestaurantHolder {
        val inflatedView = parent.inflate(R.layout.restaurant_recycle_item, false)
        return SquareRestaurantHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: SquareRestaurantHolder, position: Int) {
        val venue = restaurants[position]
        holder.bindRestaurant(venue)

    }
}