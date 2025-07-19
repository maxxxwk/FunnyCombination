package com.traffbraza.funnycombination.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoresDao {
    @Query("SELECT EXISTS(SELECT 1 FROM high_scores WHERE score >= :score)")
    suspend fun hasBetterPreviousResult(score: Int): Boolean

    @Insert
    suspend fun insertNewHighScore(highScoreEntity: HighScoreEntity)

    @Query("SELECT * FROM high_scores ORDER BY score DESC")
    fun getHighScoresFlow(): Flow<List<HighScoreEntity>>
}
