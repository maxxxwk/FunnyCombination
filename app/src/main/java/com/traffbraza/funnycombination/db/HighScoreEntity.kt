package com.traffbraza.funnycombination.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class HighScoreEntity(
    @PrimaryKey
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "score") val score: Int
)
