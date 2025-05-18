package com.example.travelplanapp.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.travelplanapp.R
import com.example.travelplanapp.databinding.ActivityMainBinding
import com.example.travelplanapp.util.PermissionUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * 主活动
 * 
 * 应用的入口点，负责初始化导航、权限申请和底部导航栏
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
    // 位置权限请求启动器
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            // 所有权限已授予，继续初始化
            initializeApp()
        } else {
            // 权限被拒绝，显示说明对话框
            showPermissionExplanationDialog()
        }
    }
    
    // 通知权限请求启动器
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 通知权限已授予，继续申请位置权限
            requestLocationPermissions()
        } else {
            // 通知权限被拒绝，显示说明对话框
            showNotificationPermissionExplanationDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 初始化视图绑定
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 设置导航控制器
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        // 设置底部导航栏
        binding.bottomNavigation.setupWithNavController(navController)
        
        // 检查并请求权限
        checkAndRequestPermissions()
    }
    
    /**
     * 检查并请求必要权限
     */
    private fun checkAndRequestPermissions() {
        // 首先检查通知权限（Android 13及以上）
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }
        
        // 然后检查位置权限
        requestLocationPermissions()
    }
    
    /**
     * 请求位置权限
     */
    private fun requestLocationPermissions() {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        
        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        
        if (!fineLocationGranted || !coarseLocationGranted) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            // 所有权限已授予，继续初始化
            initializeApp()
        }
    }
    
    /**
     * 显示位置权限说明对话框
     */
    private fun showPermissionExplanationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.permission_required_title)
            .setMessage(R.string.location_permission_explanation)
            .setPositiveButton(R.string.retry) { _, _ ->
                requestLocationPermissions()
            }
            .setNegativeButton(R.string.exit) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
    /**
     * 显示通知权限说明对话框
     */
    private fun showNotificationPermissionExplanationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.permission_required_title)
            .setMessage(R.string.notification_permission_explanation)
            .setPositiveButton(R.string.retry) { _, _ ->
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton(R.string.continue_without_notifications) { _, _ ->
                requestLocationPermissions()
            }
            .setCancelable(false)
            .show()
    }
    
    /**
     * 初始化应用
     * 
     * 在所有必要权限授予后调用
     */
    private fun initializeApp() {
        // 应用初始化逻辑
        // 例如：启动位置服务、加载用户设置等
    }
}
