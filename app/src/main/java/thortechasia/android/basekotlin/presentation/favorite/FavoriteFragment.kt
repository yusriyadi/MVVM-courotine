package thortechasia.android.basekotlin.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.base.BaseFragment
import thortechasia.android.basekotlin.presentation.detail.DetailClubActivity
import thortechasia.android.basekotlin.presentation.main.MainViewModel
import thortechasia.android.basekotlin.presentation.main.TeamItemAdapter
import thortechasia.android.basekotlin.presentation.main.TeamItemAdapterSimplify
import thortechasia.android.basekotlin.utils.ConstVal
import thortechasia.android.basekotlin.utils.UiState

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val vm by viewModel<FavoriteViewModel>()
    val groupAdapter = GroupAdapter<ViewHolder>()
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
        return inflater.inflate(R.layout.fragment_favorite, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(title = "List Favorite")

        vm.getFavorite()
        initRv()

        vm.observeLisFav().observe(viewLifecycleOwner, Observer {
            when (it) {
                is UiState.Loading -> {
                    loadingStart()
                }
                is UiState.Success -> {
                    loadingDismiss()
                    it.data.map {
                        groupAdapter.add(TeamItemAdapterSimplify(
                            Team(
                                teamName = it.teamName,
                                teamId = it.teamId.toString(),
                                teamDescription = it.teamDescription,
                                teamStadiumName = it.stadiumName,
                                teamLogo = it.teamImage
                            )
                        ) {
                            context?.startActivity<DetailClubActivity>("data" to it)
                        })
                    }

                }
                is UiState.Error -> {
                    loadingDismiss()

                }
            }
        })

    }

    private fun initRv() {
        rvFav.apply {
            layoutManager = LinearLayoutManager(requireContext())
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
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FavoriteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}