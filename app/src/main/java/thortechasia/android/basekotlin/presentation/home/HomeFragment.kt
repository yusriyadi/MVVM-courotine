package thortechasia.android.basekotlin.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.e
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_action_bar.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.detail.DetailClubActivity
import thortechasia.android.basekotlin.presentation.main.MainViewModel
import thortechasia.android.basekotlin.presentation.main.TeamItemAdapter
import thortechasia.android.basekotlin.presentation.main.TeamItemAdapterSimplify
import thortechasia.android.basekotlin.utils.UiState
import thortechasia.android.basekotlin.utils.gone
import thortechasia.android.basekotlin.utils.visible


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
    private val vm: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRv()

        if (vm.dataTeam.isEmpty()) {
            vm.getTeams("English Premier League")
        }else{
            vm.dataTeam.forEach {
                setDataToItemAdapterSimplyWay(it)
            }
        }
        listTeamObserver()

    }


    private fun listTeamObserver() {
        vm.observeTeamLiveData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is UiState.Loading -> {
                    progressBar.visible()
                }
                is UiState.Success -> {
                    progressBar.gone()
                    it.data.forEach {
//                        setDataToItemAdapter(it)
                        //simply way
                        setDataToItemAdapterSimplyWay(it)

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
            val bundle = bundleOf("data" to it)
            view?.findNavController()
                ?.navigate(R.id.action_homeFragment_to_detailClubFragment, bundle)
        })
    }

    private fun setDataToItemAdapter(it: Team) {

        val setDataToAdapter = TeamItemAdapter(it, object : TeamItemAdapter.OnClickListerner {
            override fun onClick(team: Team) {
//                toast(team.teamName)
                context?.startActivity<DetailClubActivity>("data" to team)
                vm.saveStringToPref(team.teamName)
//                lifecycleScope.launch {
//                    delay(500)
//                    this@MainActivity.runOnUiThread {
//                        toast(vm.getStringFromPref())
//                    }
//                }
            }
        })
        groupAdapter.add(setDataToAdapter)
    }

    private fun initRv() {
        rvTeams.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}