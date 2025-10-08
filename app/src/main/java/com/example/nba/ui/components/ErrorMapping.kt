package com.example.nba.ui.components

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUiError(): UiError = when (this) {
    is IOException -> UiError.Offline
    is HttpException ->
        if (code() == 429) {
            val retryAfter = response()?.headers()?.get("Retry-After")?.toIntOrNull()
            UiError.RateLimited(retryAfter)
        } else UiError.Unknown
    else -> UiError.Unknown
}