package thortechasia.android.basekotlin.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.presentation.favorite.FavoriteFragment
import thortechasia.android.basekotlin.presentation.home.HomeFragment
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
                    R.id.homeFragment->{
                        HomeFragment.newInstance()
                        bottomNavigationView.visible()
                    }
                    R.id.favoriteFragment -> {
                        FavoriteFragment.newInstance()
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
