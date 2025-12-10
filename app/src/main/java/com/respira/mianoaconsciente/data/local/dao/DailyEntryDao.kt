package com.respira.mianoaconsciente.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.respira.mianoaconsciente.data.local.entities.DailyEntry

@Dao
interface DailyEntryDao {
    @Insert
    suspend fun insert(entry: DailyEntry)

    @Query("SELECT * FROM daily_entries ORDER BY date DESC")
    suspend fun getAll(): List<DailyEntry>
}
