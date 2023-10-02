package com.example.submissionone.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionone.local.Setting
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: Setting) : ViewModel() {

    fun getTema() = pref.getTemaSetting().asLiveData()

    fun saveTema(isDark: Boolean) {
        viewModelScope.launch {
            pref.saveTemaSetting(isDark)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val pref: Setting) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(pref) as T
    }

}