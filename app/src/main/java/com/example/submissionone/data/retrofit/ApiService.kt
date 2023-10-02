package com.example.submissionone.data.retrofit

import com.example.submissionone.data.response.SearchResponse
import com.example.submissionone.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object{
        const val apiKey = "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"
    }
    @GET("users/{user}")
    @Headers("Authorization: token $apiKey")
    suspend fun getUserGithubDetail(
        @Path("user") usergithub: String
    ): UserResponse

    @GET("users/{user}/followers")
    @Headers("Authorization: token $apiKey")
    suspend fun getUserGithubFollowers(
        @Path("user") usergithub: String
    ): ArrayList<UserResponse>

    @GET("users/{user}/following")
    @Headers("Authorization: token $apiKey")
    suspend fun getUserGithubFollowing(
        @Path("user") usergithub: String
    ): ArrayList<UserResponse>

    @GET("search/users")
    @Headers("Authorization: token $apiKey")
    fun getUserGithubSearch(
        @Query("q") q: String
    ): Call<SearchResponse>

    @GET("users")
    @Headers("Authorization: token $apiKey")
    suspend fun getUserGithub(): ArrayList<UserResponse>
}

