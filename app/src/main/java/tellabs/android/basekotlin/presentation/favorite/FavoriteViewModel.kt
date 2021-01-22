package tellabs.android.basekotlin.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tellabs.android.basekotlin.data.db.entity.TeamEntity
import tellabs.android.basekotlin.data.pref.PreferencesHelper
import tellabs.android.basekotlin.data.repository.TeamRepository
import tellabs.android.basekotlin.domain.Team
import tellabs.android.basekotlin.utils.UiState

class FavoriteViewModel(
    private val teamRepository: TeamRepository,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    //use this kalo mau live data way
    fun observeLiveDataFavoriteFromDb(): LiveData<List<TeamEntity>> = teamRepository.getListLiveData()

    val listFavLiveDataState = MutableLiveData<UiState<List<TeamEntity>>>()
    fun observeLisFav() = listFavLiveDataState

    fun getFavorite() {
        viewModelScope.launch {
            listFavLiveDataState.value = UiState.Loading()
            kotlin.runCatching {
                teamRepository.getFavList()
            }.onSuccess {
                listFavLiveDataState.value = UiState.Success(it)
            }.onFailure {
                listFavLiveDataState.value = UiState.Error(it)
            }

        }
    }
}