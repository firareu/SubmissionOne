package com.example.submissionone.ui.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.local.FavRepository
import com.example.submissionone.local.entity.FavEntity
import kotlinx.coroutines.launch

class FavViewModel(private val favRepository: FavRepository) : ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _favoriteUsers = MutableLiveData<List<FavEntity>>()
    val favoriteUsers: LiveData<List<FavEntity>> = _favoriteUsers

    fun addFavoriteUser(favoriteUsers: FavEntity) {
        favRepository.insertFavorite(favoriteUsers)
    }

    fun removeFavoriteUser(favoriteUsers: FavEntity) {
        favRepository.deleteFavorite(favoriteUsers)
    }

    fun getFavoriteUsers() {
        _isLoading.postValue(true)
        favRepository.getFavoriteUser().observeForever { favoriteUsersList ->
            _favoriteUsers.postValue(favoriteUsersList)
            _isLoading.postValue(false)
            Log.d("FavoriteViewModel", "Favorite Users: $favoriteUsersList")
        }
    }

    fun checkIsFavorite(username: String) {
        favRepository.getFavoriteUserByUsername(username).observeForever { favoriteUser ->
            val isFavorite = favoriteUser != null
            _isFavorite.postValue(isFavorite)
        }
    }

    //val loading = favRepository.isLoading
    //fun getAllUser(q: String= "") = favRepository.getAllUser(q)

    //fun getFavoriteUser() = favRepository.getAllFavoriteUser()

}
