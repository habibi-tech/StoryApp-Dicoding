package com.habibi.storyapp.features.story.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.habibi.core.utils.showToast
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.ActivityStoryBinding
import com.habibi.storyapp.features.story.presentation.add.StoryAddFragment
import com.habibi.storyapp.features.story.presentation.detail.StoryDetailFragment
import com.habibi.storyapp.features.story.presentation.list.StoryListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StoryActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityStoryBinding

    private val viewModel: StoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setNavigation()
        viewModel.getUserName()
        requestPermissions()
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun requestPermissions() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_language -> {
                goToPreferenceLanguage()
                true
            }
            R.id.action_logout -> {
                showDialogProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToPreferenceLanguage() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun showDialogProfile() {
        MaterialAlertDialogBuilder(this)
            .setTitle(viewModel.userName)
            .setIcon(R.drawable.ic_person)
            .setPositiveButton(R.string.logout) { _, _ ->
                goToLogin()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
            }
            .show()
    }

    private fun goToLogin() {
        val extras = ActivityNavigator.Extras.Builder()
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .build()

        when (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)?.childFragmentManager?.fragments?.get(0)) {
            is StoryListFragment -> {
                viewModel.setUserNotLogin()
                navController.navigate(
                    resId = R.id.action_StoryListFragment_to_authenticationActivity,
                    args = null,
                    navOptions = null,
                    navigatorExtras = extras
                )
            }
            is StoryDetailFragment -> {
                viewModel.setUserNotLogin()
                navController.navigate(
                    resId = R.id.action_StoryDetailFragment_to_authenticationActivity,
                    args = null,
                    navOptions = null,
                    navigatorExtras = extras
                )
            }
            is StoryAddFragment -> {
                viewModel.setUserNotLogin()
                navController.navigate(
                    resId = R.id.action_storyAddFragment_to_authenticationActivity,
                    args = null,
                    navOptions = null,
                    navigatorExtras = extras
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                showToast(getString(R.string.permission_denied))
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}