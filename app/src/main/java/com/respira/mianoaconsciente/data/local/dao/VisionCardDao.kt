package com.respira.mianoaconsciente.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.respira.mianoaconsciente.data.local.entities.VisionCard

@Dao
interface VisionCardDao {
    @Insert
    suspend fun insert(card: VisionCard)

    @Query("SELECT * FROM vision_cards ORDER BY id DESC")
    suspend fun getAll(): List<VisionCard>
}
