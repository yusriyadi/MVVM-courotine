package thortechasia.android.basekotlin.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail_club.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.main.MainViewModel

class DetailClubActivity : AppCompatActivity() {
    lateinit var data : Team
    val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_club)

        data = intent.getParcelableExtra("data")

        textView.text = data.teamName

        btnSave.setOnClickListener {
            vm.addFav(TeamEntity(data.teamId.toInt(),data.teamName,data.teamLogo,data.teamDescription,data.teamStadiumName))
        }

        vm.addDataState.observe(this, Observer {
            toast(it)
        })

    }
}