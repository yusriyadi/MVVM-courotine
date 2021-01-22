package tellabs.android.basekotlin.presentation.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_detail_club.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.data.db.entity.TeamEntity
import tellabs.android.basekotlin.domain.Team
import tellabs.android.basekotlin.presentation.base.BaseActivity
import tellabs.android.basekotlin.presentation.main.MainViewModel
import tellabs.android.basekotlin.utils.*

class DetailClubActivity : BaseActivity() {

    lateinit var data: Team
    val vm by viewModel<MainViewModel>()
    var isThisTeamFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail_club)
        data = intent.getParcelableExtra("data")
        initUi()

        vm.isThisTeamFav(data.teamName)

        observeAddFavLIveData()
        observeDeleteLIveData()


        //selain menggunakan callback cara ini. bisa juga flag isFavorite dibuat menjadi live data, dan modif sedikit lah
        observeDataFav(isFavorite = { fav ->
            btnSave.setOnClickListener {
                if (fav) {
                    vm.deleteFav(getThisTeamData())
                } else {
                    vm.addFav(getThisTeamData())
                }

            }
        })

    }

    private fun initUi() {
        tvTeamName.text = data.teamName
        ivTeam.loadImageFromUrl(data.teamLogo)
        tvDescription.text = data.teamDescription
        setupToolbar(title = data.teamName,isBackVisble = true)
    }

    private fun observeDeleteLIveData() {
        vm.observeDeleteFav().observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
                    loadingStart()
                }
                is UiState.Success -> {
                    loadingDismiss()
                    toast("hapus berhasil")
                    vm.isThisTeamFav(data.teamName)
                }
                is UiState.Error -> {
                    loadingDismiss()
                    toast("hapus gagal : ${it.throwable.message}")
                }
            }
        })
    }

    private fun getThisTeamData(): TeamEntity {
        return TeamEntity(
            data.teamId.toInt(),
            data.teamName,
            data.teamLogo,
            data.teamDescription,
            data.teamStadiumName
        )
    }



    private fun observeDataFav(isFavorite: (Boolean) -> Unit) {
        vm.observeDataFav().observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
                    loadingStart()
                }
                is UiState.Success -> {
                    loadingDismiss()
                    if (!it.data.isEmpty()) {
                        isFavorite(true)
                        btnSave.loadImageFromDrawable(R.drawable.ic_baseline_favorite_24)
                        isThisTeamFav = true
                    } else {
                        isFavorite(false)
                        btnSave.loadImageFromDrawable(R.drawable.ic_baseline_favorite_border_24)
                        isThisTeamFav = false
                    }
                }
                is UiState.Error -> {
                    loadingDismiss()
                    toast(it.throwable.message.toString())
                }
            }
        })
    }

    private fun observeAddFavLIveData() {
        vm.observeAddFavLiveData().observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
                    loadingStart()
                }
                is UiState.Success -> {
                    loadingDismiss()
                    toast("${data.teamName} ${it.data}")
                    vm.isThisTeamFav(data.teamName)
                }
                is UiState.Error -> {
                    toast("gagal ${it}")
                }
            }
        })
    }

}