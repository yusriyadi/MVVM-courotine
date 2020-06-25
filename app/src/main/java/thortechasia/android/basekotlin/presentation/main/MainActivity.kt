package thortechasia.android.basekotlin.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.e
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.utils.UiState
import thortechasia.android.basekotlin.utils.gone
import thortechasia.android.basekotlin.utils.visible

class MainActivity : AppCompatActivity() {

    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
    private val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRv()

        vm.getTeams("English Premier League")
        listTeamObserver()
    }

    private fun listTeamObserver() {
        vm.teamState.observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
                    progressBar.visible()
                }
                is UiState.Success -> {
                    progressBar.gone()
                    it.data.forEach {
                        setDataToItemAdapter(it)

                        //simply way
                        // setDataToItemAdapterSimplyWay(it)

                    }
                }
                is UiState.Error -> {
                    progressBar.gone()
                    e(it.throwable)
                }
            }
        })
    }

    private fun setDataToItemAdapterSimplyWay(it: Team) {
        groupAdapter.add(TeamItemAdapterSimplify(it) {
            toast(it.teamName)
        })
    }

    private fun setDataToItemAdapter(it: Team) {

        val setDataToAdapter = TeamItemAdapter(it, object : TeamItemAdapter.OnClickListerner {
            override fun onClick(team: Team) {
                toast(team.teamName)
            }
        })
        groupAdapter.add(setDataToAdapter)
    }

    private fun initRv() {
        rvTeams.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = groupAdapter
        }
    }

}
