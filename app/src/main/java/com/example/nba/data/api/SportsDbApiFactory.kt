package com.example.nba.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SportsDbApiFactory {
    private const val BASE_V1 = "https://www.thesportsdb.com/api/v1/json/"

    fun createV1(client: OkHttpClient): TheSportsDbApiV1 {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(BASE_V1)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TheSportsDbApiV1::class.java)
    }
}