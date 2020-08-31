package thortechasia.android.basekotlin.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.internal.operators.single.SingleDoOnError
import io.reactivex.internal.operators.single.SingleDoOnSuccess
import kotlinx.coroutines.launch
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.data.pref.PreferencesHelper
import thortechasia.android.basekotlin.data.repository.TeamRepository
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.utils.UiState

class MainViewModel(
    private val teamRepository: TeamRepository,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {
    private val dataFav = MutableLiveData<UiState<List<TeamEntity>>>()
    private val teamState = MutableLiveData<UiState<List<Team>>>()
    private val addDataState = MutableLiveData<UiState<String>>()
    var dataTeam = mutableListOf<Team>()

    fun observeAddFavLiveData() = addDataState
    fun observeTeamLiveData() = teamState
    fun observeDataFav() = dataFav

    fun getTeams(league: String) {
        viewModelScope.launch {
            teamState.value = UiState.Loading()
            runCatching {
                teamRepository.getTeams(league)
            }.onSuccess {
                teamState.value = UiState.Success(it)
                dataTeam.addAll(it)
            }.onFailure {
                teamState.value = UiState.Error(it)
            }
        }
    }

    //how to save and get from pref, use the method inside Pref. class
    fun saveStringToPref(text: String) {
        preferencesHelper.saveString(PreferencesHelper.USERNAME, text)
    }

    fun getStringFromPref() {
        preferencesHelper.getString(PreferencesHelper.USERNAME)
    }

    fun addFav(data: TeamEntity) {
        viewModelScope.launch {
            kotlin.runCatching {
                teamRepository.addFav(data)
            }.onSuccess {
                addDataState.value = UiState.Success("ditambahkan ke favorite")
            }.onFailure {
                addDataState.value = UiState.Error(it)
            }

        }
    }

    private val deleteLiveData = MutableLiveData<UiState<String>>()
    fun observeDeleteFav()= deleteLiveData
    fun deleteFav(team : TeamEntity){
        viewModelScope.launch {
            kotlin.runCatching {
                teamRepository.deletFav(team)
                deleteLiveData.value = UiState.Loading(true)
            }.onSuccess {
                deleteLiveData.value = UiState.Success("hapus berhasil!")
            }.onFailure {
                deleteLiveData.value = UiState.Error(it)

            }
        }
    }

    fun lookThelist(teamName : String) {
        viewModelScope.launch {
            kotlin.runCatching {
                teamRepository.checkFavTeam(teamName)
            }.onSuccess {
                dataFav.value = UiState.Success(it)
            }.onFailure {
                dataFav.value = UiState.Error(it)
            }
        }
    }

}