package com.respira.mianoaconsciente.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.respira.mianoaconsciente.data.local.dao.DailyEntryDao
import com.respira.mianoaconsciente.data.local.dao.VisionCardDao
import com.respira.mianoaconsciente.data.local.entities.DailyEntry
import com.respira.mianoaconsciente.data.local.entities.VisionCard

@Database(
    entities = [DailyEntry::class, VisionCard::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyEntryDao(): DailyEntryDao
    abstract fun visionCardDao(): VisionCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "respira-db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
