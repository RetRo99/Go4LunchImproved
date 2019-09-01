package com.example.go4lunchimproved


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*


class RestaurantDetail : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_restaurant_detail)

        val restaurant = intent.extras!!.getParcelable<Venue>("venue")
        imageviewdetail.loadPhotoFromUrl(restaurant!!.getPhotoUrl())
        titleTextView.text = restaurant.name
        locationTextView.text = restaurant.getAddressText()
        callConstraint.setOnClickListener {
            if (restaurant.getPhoneNumber().isNullOrEmpty()) {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${restaurant.getPhoneNumber()}" )
                startActivity(intent)
            }

        }

        pickFab.setOnClickListener {
            pickFab.setImageResource(R.drawable.ic_check_circle_30dp)
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

    }
}


