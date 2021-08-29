package com.ix.ibrahim7.rxjavaapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setUpStatusBar


class MainActivity : AppCompatActivity() {

    private var navHostFragment: Fragment? = null
    lateinit var mbinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        setUpStatusBar(this,0)


        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host_home)

        val navController = navHostFragment!!.findNavController()

        NavigationUI.setupWithNavController(
            mbinding.bottomNavigation,
            navController
        )

        mbinding.bottomNavigation.setOnNavigationItemReselectedListener { /*No-OP*/ }

        mbinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.moreFragment -> {
                    navController.navigate(R.id.moreFragment, null, getNavOptions())
                }
                else -> {
                    navController.navigate(item.itemId, null, null)
                }
            }
            true
        }

        navHostFragment!!.findNavController()
            .addOnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
                when (destination.id) {
                    R.id.splashFragment -> {
                        window.apply {
                            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mbinding.bottomNavigation.visibility = View.GONE
                    }
                    R.id.homeFragment-> {
                        window.apply {
                            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mbinding.bottomNavigation.visibility = View.VISIBLE
                    }
                    R.id.movieFragment2,R.id.tvShowFragment-> {
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mbinding.bottomNavigation.visibility = View.VISIBLE
                    }
                    else -> {
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mbinding.bottomNavigation.visibility = View.GONE
                    }
                }
            }

    }

    private fun getNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_up)
            .setExitAnim(R.anim.slide_down)
            .setPopEnterAnim(R.anim.slide_up)
            .setPopExitAnim(R.anim.slide_down)
            .build()
    }

}