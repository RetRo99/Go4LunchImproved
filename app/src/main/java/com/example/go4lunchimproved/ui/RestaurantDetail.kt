package com.example.go4lunchimproved.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.adapters.UserAdapter
import com.example.go4lunchimproved.model.User
import com.example.go4lunchimproved.model.Venue
import com.example.go4lunchimproved.network.FireBaseManager
import com.example.go4lunchimproved.utils.loadPhotoFromUrl
import kotlinx.android.synthetic.main.activity_restaurant_detail.*


class RestaurantDetail : AppCompatActivity() {

    private lateinit var observer: Observer<ArrayList<User>>
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)


        val restaurant = intent.extras!!.getParcelable<Venue>("venue")
        imageviewdetail.loadPhotoFromUrl(restaurant!!.getPhotoUrl())
        var isPickedRestaurant = FireBaseManager.getPickedRestaurant().equals(restaurant.id)  && FireBaseManager.getPickedRestaurant() != ""
        titleTextView.text = restaurant.name
        locationTextView.text = restaurant.getAddressText()
        callConstraint.setOnClickListener {
            if (restaurant.getPhoneNumber().isNullOrEmpty()) {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${restaurant.getPhoneNumber()}")
                startActivity(intent)
            }

        }
        observer = Observer {users ->
            val filteredUsers: List<User> =   users.filter { user -> user.restaurantId.equals(restaurant.id)}
            if(::adapter.isInitialized){
                adapter.update(filteredUsers)
            }else{
                adapter = UserAdapter(filteredUsers, true)
                val linearLayoutManager = LinearLayoutManager(this)
                detailsViewRecycle.layoutManager = linearLayoutManager
                detailsViewRecycle.adapter = adapter
            }


        }



        if (isPickedRestaurant) setPickedIcon()



        pickFab.setOnClickListener {
            if (!isPickedRestaurant) {
                FireBaseManager.updateUser(restaurant)
                setPickedIcon()
                isPickedRestaurant = true
            }else{
                 FireBaseManager.updateUser(restaurant, true)
                pickFab.setImageResource(R.drawable.ic_check_circle_black_24dp)
                isPickedRestaurant = false

            }

        }
        websiteConstraint.setOnClickListener {
            if (restaurant.getWebsite().isNullOrEmpty()) {
                Toast.makeText(this, "No website available", Toast.LENGTH_SHORT).show()


            } else {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(restaurant.getWebsite())
                )
                startActivity(browserIntent)

            }
        }
        FireBaseManager.getAllUsers().observe(this, observer)

    }

    private fun setPickedIcon() {
        pickFab.setImageResource(R.drawable.ic_check_circle_30dp)
    }
}



