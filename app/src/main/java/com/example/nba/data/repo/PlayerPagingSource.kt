package com.example.nba.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nba.data.api.BallDontLieApi
import com.example.nba.data.mappers.toDomain
import com.example.nba.domain.Player
import com.example.nba.domain.Team

class PlayerPagingSource(
    private val api: BallDontLieApi,
    private val apiKey: String,
    private val pageSize: Int
) : PagingSource<Long, Player>() {


    override fun getRefreshKey(state: PagingState<Long, Player>): Long? = null


    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Player> = try {
        val cursor = params.key
        val response = api.getPlayers(apiKey, perPage = pageSize, cursor = cursor)
        val players = response.data.map { it.toDomain() }
        val next = response.meta?.next_cursor
        LoadResult.Page(
            data = players,
            prevKey = null,
            nextKey = next
        )
    } catch (t: Throwable) {
        LoadResult.Error(t)
    }
}
