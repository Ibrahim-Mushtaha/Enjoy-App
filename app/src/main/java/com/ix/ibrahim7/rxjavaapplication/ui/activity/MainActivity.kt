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
import com.ix.ibrahim7.rxjavaapplication.other.*
import com.ix.ibrahim7.rxjavaapplication.other.getDefaultLang
import com.ix.ibrahim7.rxjavaapplication.other.getNavOptions
import kotlinx.android.synthetic.main.activity_main.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setUpStatusBar
import com.ix.ibrahim7.rxjavaapplication.util.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var navHostFragment: Fragment? = null
    lateinit var mbinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        window.apply {
            getDefaultLang{ lang->
                if (!PreferencesManager(application).sharedPreference.getBoolean(PICKLANG,false)) {
                    PreferencesManager(application).editor.putString(LANG, lang).apply()
                    PreferencesManager(application).editor.putBoolean(PICKLANG, true).apply()
                }
            }
        }
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
                        setLanguage(PreferencesManager(application).sharedPreference.getString(
                            LANG, AR)!!)
                        mbinding.bottomNavigation.visibility = View.GONE
                    }
                    R.id.homeFragment-> {
                        window.apply {
                            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        setLanguage(PreferencesManager(application).sharedPreference.getString(
                            LANG, AR)!!)
                        mbinding.bottomNavigation.visibility = View.VISIBLE
                    }
                    R.id.detailsFragment-> {
                        window.apply {
                            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        setLanguage(PreferencesManager(application).sharedPreference.getString(
                            LANG, AR)!!)
                        mbinding.bottomNavigation.visibility = View.GONE
                    }
                    R.id.movieFragment2,R.id.tvShowFragment-> {
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        setLanguage(PreferencesManager(application).sharedPreference.getString(
                            LANG, AR)!!)
                        mbinding.bottomNavigation.visibility = View.VISIBLE
                    }
                    else -> {
                        setLanguage(PreferencesManager(application).sharedPreference.getString(
                            LANG, AR)!!)
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mbinding.bottomNavigation.visibility = View.GONE
                    }
                }
            }

    }

}