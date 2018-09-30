package com.renovavision.newssearch.data.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor constructor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        // add custom headers
        val requestBuilder: Request.Builder? = request?.newBuilder()
        requestBuilder?.header(ACCESS_TOKEN_HEADER, accessToken)
        request = requestBuilder?.build()
        return request?.let {
            chain?.proceed(it)
        }
    }

    companion object {
        const val ACCESS_TOKEN_HEADER = "api-key"
    }

}