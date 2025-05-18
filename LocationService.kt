package com.example.travelplanapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 位置服务
 * 
 * 提供获取用户当前位置、计算位置间距离等功能
 */
@Singleton
class LocationService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // 融合位置提供者客户端
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    
    /**
     * 获取用户最后已知位置
     * 
     * 注意：调用此方法前必须确保已获得位置权限
     * 
     * @return 最后已知位置，如果无法获取则返回null
     */
    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Location? {
        return try {
            fusedLocationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * 获取用户位置更新流
     * 
     * 注意：调用此方法前必须确保已获得位置权限
     * 
     * @param intervalMs 位置更新间隔（毫秒）
     * @return 位置更新Flow
     */
    @SuppressLint("MissingPermission")
    fun getLocationUpdates(intervalMs: Long = 10000): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.Builder(intervalMs)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateIntervalMillis(intervalMs / 2)
            .build()
            
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    trySend(location)
                }
            }
        }
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        
        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
    
    /**
     * 计算两个位置之间的直线距离
     * 
     * @param lat1 第一个位置的纬度
     * @param lon1 第一个位置的经度
     * @param lat2 第二个位置的纬度
     * @param lon2 第二个位置的经度
     * @return 两点间的距离（米）
     */
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }
    
    /**
     * 计算两个位置之间的直线距离
     * 
     * @param location1 第一个位置
     * @param location2 第二个位置
     * @return 两点间的距离（米）
     */
    fun calculateDistance(location1: Location, location2: Location): Float {
        return location1.distanceTo(location2)
    }
}
