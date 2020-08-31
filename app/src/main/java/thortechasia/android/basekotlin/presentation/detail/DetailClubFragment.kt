package thortechasia.android.basekotlin.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_detail_club.*
import kotlinx.android.synthetic.main.layout_action_bar.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.base.BaseFragment
import thortechasia.android.basekotlin.presentation.main.MainActivity
import thortechasia.android.basekotlin.presentation.main.MainViewModel
import thortechasia.android.basekotlin.utils.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailClubFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailClubFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    var idMenu : Int =0

    lateinit var data: Team
    val vm by viewModel<MainViewModel>()
    var isThisTeamFav = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idMenu = it.getInt(ConstVal.MENU_ID)
            data = arguments?.getParcelable("data")!!
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                   findNavController().navigate(getBackMenuId(idMenu))
            }
        })

    }

    private fun getBackMenuId(key :Int): Int {
        return when (key){
            ConstVal.HOME->  R.id.action_detailClubFragment_to_homeFragment
           else-> R.id.action_detailClubFragment_to_favoriteFragment2
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_club, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(title = data.teamName)

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
        vm.observeDeleteFav().observe(viewLifecycleOwner, Observer {
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

    fun accessParentActivity() = (activity as? MainActivity)

    private fun observeDataFav(isFavorite: (Boolean) -> Unit) {
        vm.observeDataFav().observe(viewLifecycleOwner, Observer {
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
                    context?.toast(it.throwable.message.toString())
                }
            }
        })
    }

    private fun observeAddFavLIveData() {
        vm.observeAddFavLiveData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is UiState.Loading -> {
                    loadingStart()
                }
                is UiState.Success -> {
                    loadingDismiss()
                    context?.toast("${data.teamName} ${it.data}")
                    vm.lookThelist(data.teamName)
                }
                is UiState.Error -> {
                    context?.toast("gagal ${it}")
                }
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailClubFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            DetailClubFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}