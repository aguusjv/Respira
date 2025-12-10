package com.respira.mianoaconsciente.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_entries")
data class DailyEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val mood: Int,
    val note: String?
)
