package com.renovavision.newssearch.data.model.local

import com.google.gson.annotations.SerializedName
import java.io.Serializable

abstract class PageList<T> : Serializable {

    var pageSize: Long = 0

    var currentPage: Long = 0

    @SerializedName("results")
    var data: ArrayList<T> = ArrayList()
}