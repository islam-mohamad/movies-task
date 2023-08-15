package com.islam.tasks.yassir.network

import com.islam.tasks.yassir.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalHttpUrl: HttpUrl = chain.request().url

        val url =
            originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        val requestBuilder: Request.Builder = chain.request().newBuilder().url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

}