package com.example.submissionone.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissionone.data.response.SearchResponse
import com.example.submissionone.data.retrofit.ApiService
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.local.room.FavDao
import com.example.submissionone.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavRepository private constructor(
    private val favoriteDao : FavDao,
    private val appExecutors: AppExecutors
) {
    fun getFavoriteUser(): LiveData<List<FavEntity>> {
        return favoriteDao.getAllFavoriteUser()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavEntity> {
        return favoriteDao.getFavoriteUserByUsername(username)
    }

    fun insertFavorite(favoriteUsers: FavEntity) {
        appExecutors.diskIO.execute {
            favoriteDao.insert(favoriteUsers)
        }
    }

    fun deleteFavorite(favoriteUsers: FavEntity) {
        appExecutors.diskIO.execute {
            favoriteDao.delete(favoriteUsers)
        }
    }

    companion object {
        @Volatile
        private var instance: FavRepository? = null
        fun getInstance(
            favDao: FavDao,
            appExecutors: AppExecutors
        ): FavRepository =
            instance ?: synchronized(this) {
                instance ?: FavRepository(favDao, appExecutors)
            }.also { instance = it }
    }


}