package com.islam.tasks.core.network

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.mapNetworkErrors() = when (this) {
    is ConnectException, is SocketTimeoutException, is UnknownHostException, is IOException -> NoInternetException
    is HttpException -> {
        try {
            val response = this.response()
            val message = response?.errorBody()?.string()
            val errorJson = JSONObject(message!!)
            val errorMessage = if (response.code() == 422) {
                val errors = errorJson.getJSONArray("errors")
                if (errors.length() > 0) errors.getString(0)
                else ""
            } else {
                errorJson.getString("status_message")
            }
            ServerError(errorMessage)
        } catch (t: Throwable) {
            ServerError("Error parsing error response")
        }
    }

    else -> this
}