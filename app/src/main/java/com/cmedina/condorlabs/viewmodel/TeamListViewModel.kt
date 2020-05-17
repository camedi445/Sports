package com.cmedina.condorlabs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.data.repository.TeamDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamListViewModel(private val teamRepository: TeamDataSource) : ViewModel() {

    private val _teamList = MutableLiveData<List<Team>>()
    var teamList: LiveData<List<Team>> = _teamList
    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading
    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun getTeamsByLeagueId(leagueId: String) {
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            val result: OperationResult<List<Team>>? = withContext(Dispatchers.IO) {
                teamRepository.fetchTeamsByLeagueId(leagueId)
            }
            _isViewLoading.postValue(false)
            result?.let {
                when (result) {
                    is OperationResult.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            //
                        } else {
                            _teamList.value = result.data
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

