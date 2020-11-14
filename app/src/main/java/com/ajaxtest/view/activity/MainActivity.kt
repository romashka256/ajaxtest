package com.ajaxtest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ajaxtest.App
import com.ajaxtest.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.mainActivityComponentFactory().create(this, this).inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}