package com.renovavision.newssearch.data.model.livedata

open class Resource<out T, out E> constructor(val status: ResourceState, val data: T? = null, val error: E? = null) {

    companion object {

        @JvmStatic
        fun <T, E> success(data: T): Resource<T, E> {
            return Resource(ResourceState.SUCCESS, data, null)
        }

        @JvmStatic
        fun <T, E> error(error: E): Resource<T, E> {
            return Resource(ResourceState.ERROR, null, error)
        }

        @JvmStatic
        fun <T, E> loading(): Resource<T, E> {
            return Resource(ResourceState.LOADING, null, null)
        }
    }
}