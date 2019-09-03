package com.example.go4lunchimproved


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go4lunchimproved.FireBaseManager.getAllUsers
import kotlinx.android.synthetic.main.fragment_workmates_list.*


class WorkmatesListFragment : Fragment() {

    private lateinit var observer: Observer<List<User>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_workmates_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        observer = Observer { users ->
            val adapter = UserAdapter(users)
            val linearLayoutManager = LinearLayoutManager(context)
            recycle_workmates.layoutManager = linearLayoutManager
            recycle_workmates.adapter = adapter

        }

        getAllUsers().observe(this, observer)


    }
}
