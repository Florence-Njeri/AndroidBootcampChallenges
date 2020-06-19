package com.florencenjeri.listmaker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.florencenjeri.listmaker.R

class MainActivity : AppCompatActivity() {

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAILS_REQUEST_CODE = 1234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


    }

}