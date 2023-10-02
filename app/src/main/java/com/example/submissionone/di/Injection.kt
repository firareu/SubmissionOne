package com.example.submissionone.di

import android.content.Context
import com.example.submissionone.data.retrofit.ApiConfig
import com.example.submissionone.local.FavDatabase
import com.example.submissionone.local.FavRepository
import com.example.submissionone.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavDatabase.getInstance(context)
        val dao = database.favDao()
        val appExecutors = AppExecutors()
        return FavRepository.getInstance(dao, appExecutors)
    }
}