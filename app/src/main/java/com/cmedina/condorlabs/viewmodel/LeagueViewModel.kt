package com.cmedina.condorlabs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.data.repository.LeagueDataSource
import com.cmedina.condorlabs.data.repository.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LeagueViewModel(private val leagueRepository: LeagueDataSource) : ViewModel() {

    private val _leagueList = MutableLiveData<List<League>>()
    val leagueList: LiveData<List<League>> = _leagueList
    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading
    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun getLeagueList() {
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            val result: OperationResult<List<League>>? = withContext(Dispatchers.IO) {
                leagueRepository.fetchLeagues()
            }
            _isViewLoading.postValue(false)
            result?.let {

                when (result) {
                    is OperationResult.Success -> {
                        if (!result.data.isNullOrEmpty()) {
                            _leagueList.value = result.data
                        }
                    }
                    is OperationResult.Error -> {
                        _onMessageError.postValue(result.exception)
                    }
                }
            }
        }
    }


}