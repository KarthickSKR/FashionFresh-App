package com.example.fashionfresh.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.fashionfresh.R
import com.example.fashionfresh.ui.view.activity.ProductListActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    fun initSplash(logoImg : ImageView,tvAppName:TextView,activity:Activity){
      viewModelScope.launch {
          Glide.with(logoImg.context).load(R.drawable.ic_logo).into(logoImg)
          tvAppName.text = tvAppName.context.getString(R.string.app_name)
          launchProductList(activity)
      }
    }

    private fun launchProductList(activity: Activity) {
        viewModelScope.launch {
            delay( 3000)
            val intent = Intent(activity.applicationContext, ProductListActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}