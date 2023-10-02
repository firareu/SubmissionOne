package com.example.submissionone.ui.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionone.di.Injection
import com.example.submissionone.local.FavRepository

class FavViewModelFactory private constructor(private val favRepository: FavRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(favRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavViewModelFactory? = null
        fun getDatabase(context: Context): FavViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}