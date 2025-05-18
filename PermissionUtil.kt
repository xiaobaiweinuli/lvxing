package com.example.travelplanapp.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * 权限工具类
 * 
 * 用于处理Android运行时权限的申请和检查
 */
class PermissionUtil {
    companion object {
        /**
         * 检查是否已授予权限
         * 
         * @param context 上下文
         * @param permission 要检查的权限
         * @return 是否已授予权限
         */
        fun isPermissionGranted(context: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        
        /**
         * 检查是否已授予多个权限
         * 
         * @param context 上下文
         * @param permissions 要检查的权限列表
         * @return 是否所有权限都已授予
         */
        fun arePermissionsGranted(context: Context, permissions: Array<String>): Boolean {
            return permissions.all { permission ->
                isPermissionGranted(context, permission)
            }
        }
        
        /**
         * 在Fragment中注册权限请求启动器
         * 
         * @param fragment 请求权限的Fragment
         * @param onPermissionResult 权限结果回调
         * @return 权限请求启动器
         */
        fun registerPermissionLauncher(
            fragment: Fragment,
            onPermissionResult: (Boolean) -> Unit
        ): ActivityResultLauncher<String> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                onPermissionResult(isGranted)
            }
        }
        
        /**
         * 在Fragment中注册多权限请求启动器
         * 
         * @param fragment 请求权限的Fragment
         * @param onPermissionsResult 权限结果回调，Map的键为权限名，值为是否授予
         * @return 多权限请求启动器
         */
        fun registerMultiplePermissionsLauncher(
            fragment: Fragment,
            onPermissionsResult: (Map<String, Boolean>) -> Unit
        ): ActivityResultLauncher<Array<String>> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                onPermissionsResult(permissions)
            }
        }
        
        /**
         * 在Activity中注册权限请求启动器
         * 
         * @param activity 请求权限的Activity
         * @param onPermissionResult 权限结果回调
         * @return 权限请求启动器
         */
        fun registerPermissionLauncher(
            activity: AppCompatActivity,
            onPermissionResult: (Boolean) -> Unit
        ): ActivityResultLauncher<String> {
            return activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                onPermissionResult(isGranted)
            }
        }
        
        /**
         * 在Activity中注册多权限请求启动器
         * 
         * @param activity 请求权限的Activity
         * @param onPermissionsResult 权限结果回调，Map的键为权限名，值为是否授予
         * @return 多权限请求启动器
         */
        fun registerMultiplePermissionsLauncher(
            activity: AppCompatActivity,
            onPermissionsResult: (Map<String, Boolean>) -> Unit
        ): ActivityResultLauncher<Array<String>> {
            return activity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                onPermissionsResult(permissions)
            }
        }
    }
}
