package thortechasia.android.basekotlin.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.data.pref.PreferencesHelper
import thortechasia.android.basekotlin.data.repository.TeamRepository
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.utils.UiState

class FavoriteViewModel(
    private val teamRepository: TeamRepository,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

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