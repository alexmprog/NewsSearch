package com.renovavision.newssearch.data.model.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "title")
        val title: String?,

        @ColumnInfo(name = "url")
        val url: String?,

        @ColumnInfo(name = "section")
        val section: String?,

        @ColumnInfo(name = "thumbnail")
        val thumbnail: String?,

        @ColumnInfo(name = "lastModified")
        val lastModified: String?) : Serializable