package com.example.submissionone.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class DetailViewModel(username: String) : ViewModel() {
    private val _detailUser = MutableLiveData<UserResponse?>()
    val detailUser: LiveData<UserResponse?> = _detailUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getDetailUser(username)
    }

    private fun getDetailUser(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = ApiConfig.getApiService().getUserGithubDetail(username)
                _isLoading.value = false
                _detailUser.postValue(result)
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message.toString()}")
            }
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    override fun onCleared() {
        super.onCleared()
        // Cancel any ongoing background tasks or coroutines here if needed.
    }
}
