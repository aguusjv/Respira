package com.respira.mianoconciente.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        DailyEntry::class,
        TimelineEvent::class,
        LifeWheel::class,
        Goal::class,
        JournalEntry::class,
        VisionCard::class,
        YearIntentions::class,
        InnerResources::class,
        EmotionReflection::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyEntryDao(): DailyEntryDao
    abstract fun timelineEventDao(): TimelineEventDao
    abstract fun lifeWheelDao(): LifeWheelDao
    abstract fun goalDao(): GoalDao
    abstract fun journalEntryDao(): JournalEntryDao
    abstract fun visionCardDao(): VisionCardDao
    abstract fun yearIntentionsDao(): YearIntentionsDao
    abstract fun innerResourcesDao(): InnerResourcesDao
    abstract fun emotionReflectionDao(): EmotionReflectionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "respira.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
