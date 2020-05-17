package com.cmedina.condorlabs.core

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

open class BaseActivity : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    protected inline fun <vm : ViewModel> viewModelFactory(crossinline f: () -> vm) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}