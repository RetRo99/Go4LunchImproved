package com.example.go4lunchimproved.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.go4lunchimproved.R

/**
 * A simple [Fragment] subclass.
 */
class AutocompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            return inflater.inflate(R.layout.fragment_autocomplete, container, false)
        }
    }


}
