package com.example.go4lunchimproved

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.restaurant_recycle_item.view.*

class SquareRestaurantAdapter(val restaurants:List<Venue> ): RecyclerView.Adapter<SquareRestaurantAdapter.SquareRestaurantHolder>() {



    class SquareRestaurantHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v

        fun bindRestaurant(restaurant: Venue){
            view.apply {
                recycleItemTitle.text = restaurant.name
                val addresstText = "${restaurant.categories?.get(0)?.name} - ${restaurant.location.address}"
                recycleItemAddress.text = addresstText

                val distance = "${restaurant.location.distance}  m"
                recycleItemDistance.text = distance

                val hours ="${restaurant.hours?.status}"
                recycleItemHours.text = hours
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareRestaurantHolder {
        val inflatedView = parent.inflate(R.layout.restaurant_recycle_item, false)
        return SquareRestaurantHolder(inflatedView)
    }

    override fun getItemCount(): Int {
     return restaurants.size
    }

    override fun onBindViewHolder(holder: SquareRestaurantHolder, position: Int) {
        val venue = restaurants[position]
        holder.bindRestaurant(venue)

    }
}