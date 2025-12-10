package com.respira.mianoconciente.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_entries")
data class DailyEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val mood: Int,
    val note: String?,
    val microAction: String?
)

@Entity(tableName = "timeline_events")
data class TimelineEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val title: String,
    val description: String?,
    val mainEmotion: String
)

@Entity(tableName = "life_wheel")
data class LifeWheel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val health: Int,
    val money: Int,
    val personalGrowth: Int,
    val spirituality: Int,
    val social: Int,
    val familyLove: Int,
    val leisure: Int,
    val work: Int
)

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val description: String,
    val isDone: Boolean = false
)

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val text: String
)

@Entity(tableName = "vision_cards")
data class VisionCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?
)

@Entity(tableName = "year_intentions")
data class YearIntentions(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val emotionsToFeel: String?,
    val dreams: String?,
    val areasToImprove: String?,
    val stopNormalizing: String?,
    val cultivate: String?,
    val emotionalLearning: String?,
    val guideWord: String?,
    val guideWordReason: String?
)

@Entity(tableName = "inner_resources")
data class InnerResources(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val heldMe: String?,
    val qualityNeeded: String?,
    val discovery: String?
)

@Entity(tableName = "emotion_reflections")
data class EmotionReflection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val frequentEmotions: String?,
    val versionToLeave: String?
)
