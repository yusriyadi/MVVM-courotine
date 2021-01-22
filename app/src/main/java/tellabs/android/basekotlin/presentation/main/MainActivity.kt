package tellabs.android.basekotlin.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.presentation.base.BaseActivity
import tellabs.android.basekotlin.presentation.favorite.FavoriteFragment
import tellabs.android.basekotlin.presentation.home.HomeFragment
import tellabs.android.basekotlin.utils.ViewPagerAdapter
import timber.log.Timber

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(title = "List Team", isBackVisble = false)
        setupNavBar()
    }

    private fun setupNavBar() {
        var prevMenuItem: MenuItem? = null

        viewPager.offscreenPageLimit = 2
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.let {
            it.addFragment(HomeFragment.newInstance())
            it.addFragment(FavoriteFragment.newInstance())
        }
        viewPager.adapter = adapter
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    setupToolbar(title = "List Team", isBackVisble = false)
                    viewPager.currentItem =0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favoriteFragment -> {
                    setupToolbar(title = "Team Favorit", isBackVisble = false)
                    viewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        )

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    bottomNavigationView.menu.getItem(0).isChecked = false
                }
                Timber.d("onPageSelected: $position")
                bottomNavigationView.menu.getItem(position).isChecked = true
                prevMenuItem = bottomNavigationView.menu.getItem(position)
            }

        })


    }
}
