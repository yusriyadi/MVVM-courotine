package thortechasia.android.basekotlin.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thortechasia.android.basekotlin.data.pref.PreferencesHelper
import thortechasia.android.basekotlin.data.repository.TeamRepository
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.base.BaseViewModel
import thortechasia.android.basekotlin.utils.UiState
import thortechasia.android.momakan.utils.scheduler.SchedulerProvider
import thortechasia.android.momakan.utils.scheduler.with
import java.lang.ref.PhantomReference

class MainViewModel(
    val teamRepository: TeamRepository,
    val preferencesHelper: PreferencesHelper
) : ViewModel() {

    val teamState = MutableLiveData<UiState<List<Team>>>()

    fun getTeams(league: String) {
        viewModelScope.launch {
            teamState.value = UiState.Loading()
            runCatching {
                teamRepository.getTeams(league)
            }.onSuccess {
                teamState.value = UiState.Success(it)
            }.onFailure {
                teamState.value = UiState.Error(it)
            }
        }
    }

    //how to save and get from pref, use the method inside Pref. class
    fun saveStringToPref(text : String) {
       preferencesHelper.saveString(PreferencesHelper.USERNAME, text)
    }

    fun getStringFromPref(){
       preferencesHelper.getString(PreferencesHelper.USERNAME)
    }


}