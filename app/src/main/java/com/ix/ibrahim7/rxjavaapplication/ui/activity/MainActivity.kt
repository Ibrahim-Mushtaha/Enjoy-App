package com.ix.ibrahim7.rxjavaapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ix.ibrahim7.rxjavaapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setUpStatusBar


class MainActivity : AppCompatActivity() {



    lateinit var mbinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        setUpStatusBar(this,0)
        setSupportActionBar(toolbar)
        toolbar.visibility=View.GONE


    }


}