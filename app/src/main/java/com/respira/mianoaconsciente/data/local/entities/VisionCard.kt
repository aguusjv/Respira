package com.respira.mianoaconsciente.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vision_cards")
data class VisionCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String
)
