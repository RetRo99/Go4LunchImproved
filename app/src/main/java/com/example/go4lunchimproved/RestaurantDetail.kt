package com.example.go4lunchimproved


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*

class RestaurantDetail : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_restaurant_detail)

        val restaurant = intent.extras!!.getParcelable<Venue>("venue")
       imageviewdetail.loadPhotoFromUrl(restaurant!!.getPhotoUrl())
        titleTextView.text = restaurant.name
        locationTextView.text = restaurant.location.address
        callConstraint.setOnClickListener {
            if (!restaurant.contact?.phone.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${restaurant.contact?.phone}")
                startActivity(intent)
            } else {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_LONG).show()
            }

        }
        /*websiteConstraint.setOnClickListener {
            if (!restaurant.website.isNullOrEmpty()) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(restaurant.website)
                startActivity(i)
            } else {
                Toast.makeText(view?.context, "No website number available", Toast.LENGTH_LONG).show()

            }    */
        }

    }
    


