package thortechasia.android.basekotlin.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.e
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.presentation.detail.DetailClubActivity
import thortechasia.android.basekotlin.presentation.service.MyService
import thortechasia.android.basekotlin.utils.UiState
import thortechasia.android.basekotlin.utils.gone
import thortechasia.android.basekotlin.utils.visible

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavBar()
    }

    private fun setupNavBar() {
        findNavController(R.id.fragment_host).let { navController ->
            bottomNavigationView.setupWithNavController(navController)

            //note navigation listener
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.homeFragment,
                    R.id.favoriteFragment -> {
                        bottomNavigationView.visible()
                    }
                    else -> {
                        //note selain view dengan id diatas, bottom nav akan di hilangkan
                        bottomNavigationView.gone()
                    }
                }
            }
        }
    }
}
