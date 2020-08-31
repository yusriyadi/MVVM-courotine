package thortechasia.android.basekotlin.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail_club.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.base.BaseActivity
import thortechasia.android.basekotlin.presentation.main.MainActivity
import thortechasia.android.basekotlin.presentation.main.MainViewModel
import thortechasia.android.basekotlin.utils.*

class DetailClubActivity : BaseActivity() {

    lateinit var data: Team
    val vm by viewModel<MainViewModel>()
    var isThisTeamFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail_club)
        data = intent.getParcelableExtra("data")

        setupToolbar(title = data.teamName,isBackVisble = true)

        vm.lookThelist(data.teamName)

        textView.text = data.teamName
        ivTeam.loadImageFromUrl(data.teamLogo)

        observeAddFavLIveData()
        observeDeleteLIveData()

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

    private fun observeDeleteLIveData() {
        vm.observeDeleteFav().observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
                    loadingStart()
                }
                is UiState.Success -> {
                    loadingDismiss()
                    toast("hapus berhasil")
                    vm.lookThelist(data.teamName)
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
                    vm.lookThelist(data.teamName)
                }
                is UiState.Error -> {
                    toast("gagal ${it}")
                }
            }
        })
    }

}