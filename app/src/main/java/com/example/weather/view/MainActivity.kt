package com.example.weather.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weather.R
import com.example.weather.view.adapter.HourAdapter
import com.example.weather.viewmodel.WeatherViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var navController: NavController

    private val viewmodel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    /* viewmodel.getWeather(33.44, -94.04, "471513ea69403129f79bbd3675cfccf3")
         lifecycleScope.launch {
                  viewmodel.weather.collect {
                      println(it)
                      Log.i("zxcv", "onViewCreated: $it")
                      var hourlyAdapter= HourAdapter(it)



         }}*/

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigator_layout)

        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(navigationView, navController)

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            } else {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}