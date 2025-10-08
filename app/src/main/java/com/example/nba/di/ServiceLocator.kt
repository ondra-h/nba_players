package com.example.nba.di

import android.content.Context
import com.example.nba.BuildConfig
import com.example.nba.data.api.SportsDbApiFactory
import com.example.nba.data.repo.ArtRepo
import com.example.nba.data.repo.NbaRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

object ServiceLocator {
    private lateinit var appContext: Context
    fun init(context: Context) { appContext = context.applicationContext }

    private val okHttp: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val cacheDir = File(appContext.cacheDir, "nba_cache")
        val cache = Cache(cacheDir, 5L * 1024L * 1024L)

        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logger)
            .build()
    }

    private val ballDontLieApi by lazy { BallDontLieApiFactory.create(okHttp) }
    val repo by lazy { NbaRepository(ballDontLieApi, BuildConfig.BALLDONTLIE_API_KEY) }
    private val sdbApi by lazy { SportsDbApiFactory.createV1(okHttp) }
    val sdbKey by lazy { BuildConfig.THESPORTSDB_API_KEY }
    val artRepo by lazy { ArtRepo(sdbApi, sdbKey) }
}