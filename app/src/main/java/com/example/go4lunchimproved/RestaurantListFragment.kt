package com.example.go4lunchimproved


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RestaurantListFragment : Fragment() {

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




        observer = Observer {
            if (it != null) {
                linearLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = linearLayoutManager

                adapter = SquareRestaurantAdapter(it)
                recyclerView.adapter = adapter

            }
        }

        repo.getNearbySquareRestaurant().observe(this, observer)
    }


}



