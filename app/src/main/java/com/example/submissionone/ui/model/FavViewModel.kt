package com.example.submissionone.ui.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionone.local.FavRepository
import com.example.submissionone.local.entity.FavEntity
import kotlinx.coroutines.launch

class FavViewModel(private val favRepository: FavRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


    fun addFavoriteUser(favoriteUsers: FavEntity) {
        favRepository.insertFavorite(favoriteUsers)
    }

    fun removeFavoriteUser(favoriteUsers: FavEntity) {
        favRepository.deleteFavorite(favoriteUsers)
    }

    //val loading = favRepository.isLoading
    //fun getAllUser(q: String= "") = favRepository.getAllUser(q)

    //fun getFavoriteUser() = favRepository.getAllFavoriteUser()

}
