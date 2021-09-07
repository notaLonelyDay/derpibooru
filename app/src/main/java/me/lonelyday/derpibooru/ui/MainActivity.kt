package me.lonelyday.derpibooru.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import me.lonelyday.derpibooru.DerpibooruApplication
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.ActivityMainBinding
import me.lonelyday.derpibooru.ui.search.SearchQuerySharedViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpNavigation()

        (applicationContext as DerpibooruApplication).createRequestPermissionLauncher(this)

    }

    private fun setUpNavigation() {
        setUpNavigationViewHeader()
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setUpNavigationViewHeader() {
        viewModel.loadFeaturedImage().observe(this) {
            val image =
                binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.headerImage)

            Glide.with(this).load(it.representations["large"]).into(image)
        }
    }
}