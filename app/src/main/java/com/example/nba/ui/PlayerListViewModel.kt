package com.example.nba.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nba.di.ServiceLocator

class PlayerListViewModel : ViewModel() {
    val flow = Pager(PagingConfig(pageSize = 35, prefetchDistance = 10)) {
        ServiceLocator.repo.playersPagingSource(pageSize = 35)
    }.flow.cachedIn(viewModelScope)
}