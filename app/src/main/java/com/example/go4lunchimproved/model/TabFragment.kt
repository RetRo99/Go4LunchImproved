package com.example.go4lunchimproved.model

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import com.example.go4lunchimproved.network.FireBaseManager.filterUsers
import com.example.go4lunchimproved.network.Repository.filterListRestaurants
import com.example.go4lunchimproved.ui.MainActivity
import com.example.go4lunchimproved.utils.hideKeyboard
import com.example.go4lunchimproved.utils.showKeyboard
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


abstract class TabFragment : Fragment() {

    private var VOICE_CODE: Int = 666

    fun setSearchListener(fragmentType: FragmentType) {
        if (fragmentType == FragmentType.WORKMATESVIEW) {
            searchEditText.hint = "Search Workmates"
        }
        searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                when (fragmentType) {
                    FragmentType.RESTAURANTVIEW -> filterListRestaurants(s.toString())

                    FragmentType.WORKMATESVIEW -> filterUsers(s.toString())
                    else -> true
                }


            }
        })
    }

    fun setOnClickListeners(fragmentType: FragmentType) {
        editTextClearButton.setOnClickListener {
            if (searchEditText.text.isEmpty()) {
                searchLayout.visibility =
                    View.INVISIBLE
                navigationDrawerButton.visibility = View.VISIBLE
                openSearchButton.visibility = View.VISIBLE
                openSearchButton.hideKeyboard()
            } else searchEditText.text.clear()

        }
        openSearchButton.setOnClickListener {
            searchLayout.visibility = View.VISIBLE
            navigationDrawerButton.visibility = View.INVISIBLE
            openSearchButton.visibility = View.INVISIBLE
            searchEditText.requestFocus()
            searchEditText.showKeyboard()

        }
        micSearch.setOnClickListener {
            voice_to_text()
        }
        navigationDrawerButton.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }
        setSearchListener(fragmentType)
    }


    private fun voice_to_text() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Voice2Text \n Say Something!!"
        )
        try {
            startActivityForResult(intent, VOICE_CODE)
        } catch (e: ActivityNotFoundException) {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when (requestCode) {
            VOICE_CODE -> {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    searchEditText.setText(result!![0])
                    searchEditText.setSelection(searchEditText.text.length)

                }
            }
        }
    }

}