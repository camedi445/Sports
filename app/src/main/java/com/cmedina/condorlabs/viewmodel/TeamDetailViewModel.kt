package com.cmedina.condorlabs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.data.repository.TeamDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamDetailViewModel(private val teamRepository: TeamDataSource) : ViewModel() {

    private val _eventList = MutableLiveData<List<Event>>()
    private val _teamData = MutableLiveData<Team>()
    val eventList: LiveData<List<Event>> = _eventList
    val teamData: LiveData<Team> = _teamData
    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading
    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun getTeamDetail(teamId: String) {
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            val team: OperationResult<Team>? = withContext(Dispatchers.IO) {
                teamRepository.fetchTeamDetailsById(teamId)
            }
            _isViewLoading.postValue(false)
            team?.let {

                when (team) {
                    is OperationResult.Success -> {
                        _teamData.value = team.data
                    }
                    is OperationResult.Error -> {
                        _onMessageError.postValue(team.exception)
                    }
                }
            }
        }
    }

    fun getEventsByTeamId(teamId: String) {
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            val result: OperationResult<List<Event>>? = withContext(Dispatchers.IO) {
                teamRepository.fetchEventsByTeamId(teamId)
            }
            _isViewLoading.postValue(false)
            result?.let {

                when (result) {
                    is OperationResult.Success -> {
                        if (!result.data.isNullOrEmpty()) {
                            _eventList.value = result.data
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