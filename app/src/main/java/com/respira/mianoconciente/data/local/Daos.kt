package com.respira.mianoconciente.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DailyEntry)

    @Query("SELECT * FROM daily_entries ORDER BY date DESC")
    suspend fun getAll(): List<DailyEntry>
}

@Dao
interface TimelineEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: TimelineEvent)

    @Query("SELECT * FROM timeline_events ORDER BY date ASC")
    suspend fun getAll(): List<TimelineEvent>
}

@Dao
interface LifeWheelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wheel: LifeWheel)

    @Query("SELECT * FROM life_wheel ORDER BY date DESC")
    suspend fun getAll(): List<LifeWheel>
}

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: Goal)

    @Query("SELECT * FROM goals WHERE category = :category")
    suspend fun getByCategory(category: String): List<Goal>
}

@Dao
interface JournalEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries ORDER BY date DESC")
    suspend fun getAll(): List<JournalEntry>
}

@Dao
interface VisionCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: VisionCard)

    @Query("SELECT * FROM vision_cards ORDER BY id DESC")
    suspend fun getAll(): List<VisionCard>
}

@Dao
interface YearIntentionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(intentions: YearIntentions)

    @Query("SELECT * FROM year_intentions ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(): YearIntentions?
}

@Dao
interface InnerResourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resources: InnerResources)

    @Query("SELECT * FROM inner_resources ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(): InnerResources?
}

@Dao
interface EmotionReflectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reflection: EmotionReflection)

    @Query("SELECT * FROM emotion_reflections ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(): EmotionReflection?
}
