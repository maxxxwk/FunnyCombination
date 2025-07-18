package com.traffbraza.funnycombination.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HighScoreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HighScoresDatabase : RoomDatabase() {
    abstract fun getHighScoresDao(): HighScoresDao
}
