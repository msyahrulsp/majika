package com.example.majika

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PageViewModel : ViewModel(){
    val currentPage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val cameraPermission: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}