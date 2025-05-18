package com.example.travelplanapp.di

import android.content.Context
import com.example.travelplanapp.data.dao.TicketInfoDao
import com.example.travelplanapp.data.database.AppDatabase
import com.example.travelplanapp.data.repository.TicketRepository
import com.example.travelplanapp.map.AMapService
import com.example.travelplanapp.map.MapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 应用依赖注入模块
 * 
 * 提供应用所需的各种依赖实例
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * 提供数据库实例
     * 
     * @param context 应用上下文
     * @return 数据库实例
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    
    /**
     * 提供车票信息DAO
     * 
     * @param database 数据库实例
     * @return 车票信息DAO
     */
    @Provides
    @Singleton
    fun provideTicketInfoDao(database: AppDatabase): TicketInfoDao {
        return database.ticketInfoDao()
    }
    
    /**
     * 提供车票信息仓库
     * 
     * @param ticketInfoDao 车票信息DAO
     * @return 车票信息仓库
     */
    @Provides
    @Singleton
    fun provideTicketRepository(ticketInfoDao: TicketInfoDao): TicketRepository {
        return TicketRepository(ticketInfoDao)
    }
    
    /**
     * 提供地图服务
     * 
     * 默认使用高德地图服务，可通过设置切换
     * 
     * @param context 应用上下文
     * @return 地图服务
     */
    @Provides
    @Singleton
    fun provideMapService(@ApplicationContext context: Context): MapService {
        // 默认使用高德地图服务
        // 实际应用中可根据用户设置动态切换
        return AMapService(context)
    }
}
