package com.example.go4lunchimproved.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.adapters.SquareRestaurantAdapter
import com.example.go4lunchimproved.model.FragmentType
import com.example.go4lunchimproved.model.TabFragment
import com.example.go4lunchimproved.model.Venue
import com.example.go4lunchimproved.network.Repository


class RestaurantListFragment : TabFragment() {

    private lateinit var observer: Observer<List<Venue>>
    private lateinit var repo: Repository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SquareRestaurantAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_restaurant_list, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        repo = Repository
        recyclerView = view.findViewById(R.id.recyclerView)



        setOnClickListeners(FragmentType.RESTAURANTVIEW)


        observer = Observer {
            if (it != null) {
                linearLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = linearLayoutManager

                adapter = SquareRestaurantAdapter(it)
                recyclerView.adapter = adapter

            }
        }

        repo.getListRestaurants().observe(this, observer)
    }


}



