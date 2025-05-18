package com.example.travelplanapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.travelplanapp.data.dao.TicketInfoDao
import com.example.travelplanapp.data.model.TicketInfo
import com.example.travelplanapp.util.DateConverter

/**
 * 应用数据库
 * 
 * Room数据库实现，用于本地存储车票信息和相关数据
 */
@Database(
    entities = [TicketInfo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * 获取车票信息DAO
     */
    abstract fun ticketInfoDao(): TicketInfoDao
    
    companion object {
        // 单例模式实现
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * 获取数据库实例
         * 
         * @param context 应用上下文
         * @return 数据库实例
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "travel_plan_database"
                )
                .fallbackToDestructiveMigration() // 数据库版本变化时重建数据库
                .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}
