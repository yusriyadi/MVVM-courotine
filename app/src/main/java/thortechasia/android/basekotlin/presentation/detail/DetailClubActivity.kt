package thortechasia.android.basekotlin.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail_club.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.main.MainActivity
import thortechasia.android.basekotlin.presentation.main.MainViewModel
import thortechasia.android.basekotlin.utils.UiState
import thortechasia.android.basekotlin.utils.loadImageFromDrawable
import thortechasia.android.basekotlin.utils.loadImageFromUrl

class DetailClubActivity : AppCompatActivity() {
    lateinit var data: Team
    val vm by viewModel<MainViewModel>()
    var isThisTeamFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_club)

        data = intent.getParcelableExtra("data")

        vm.lookThelist(data.teamName)

        textView.text = data.teamName
        ivTeam.loadImageFromUrl(data.teamLogo)


        btnSave.setOnClickListener {
            if (isThisTeamFav) {
                vm.addFav(
                    TeamEntity(
                        data.teamId.toInt(),
                        data.teamName,
                        data.teamLogo,
                        data.teamDescription,
                        data.teamStadiumName
                    )
                )
            } else {
                toast("delete coming soon")
            }
        }

        observeAddFavLIveData()
        observeDataFav()


    }

    private fun observeDataFav() {
        vm.observeDataFav().observe(this, Observer {
            when (it) {
                is UiState.Loading -> {

                }
                is UiState.Success -> {
                    if (it.data.size == 1) {
                        btnSave.loadImageFromDrawable(R.drawable.ic_baseline_favorite_24)
                        isThisTeamFav = true
                    } else {
                        btnSave.loadImageFromDrawable(R.drawable.ic_baseline_favorite_border_24)
                        isThisTeamFav = false
                    }
                }
                is UiState.Error -> {
                    toast(it.throwable.message.toString())
                }
            }
        })
    }

    private fun observeAddFavLIveData() {
        vm.observeAddFavLiveData().observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
                    toast("loading")
                }
                is UiState.Success -> {
                    toast("data has been added")
                    vm.lookThelist(data.teamName)
                }
                is UiState.Error -> {
                    toast("gagal ${it}")
                }
            }
        })
    }

}