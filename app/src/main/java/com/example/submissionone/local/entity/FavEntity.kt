package com.example.submissionone.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav")
data class FavEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    var username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "htmlUrl")
    var htmlUrl: String? = null,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean



)

