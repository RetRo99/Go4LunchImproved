package com.example.go4lunchimproved.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go4lunchimproved.network.FireBaseManager.getAllUsers
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.model.User
import com.example.go4lunchimproved.adapters.UserAdapter
import kotlinx.android.synthetic.main.fragment_workmates_list.*


class WorkmatesListFragment : Fragment() {

    private lateinit var observer: Observer<ArrayList<User>>
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_workmates_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        observer = Observer { users ->
            val filteredUsers = users.sortedBy { user -> user.pickedRestaurantText }.reversed()
            if(::adapter.isInitialized){
                    adapter.update(filteredUsers)
            }else{
                adapter = UserAdapter(filteredUsers)
                val linearLayoutManager = LinearLayoutManager(context)
                recycle_workmates.layoutManager = linearLayoutManager
                recycle_workmates.adapter = adapter
            }


        }

        getAllUsers().observe(this, observer)


    }
}
