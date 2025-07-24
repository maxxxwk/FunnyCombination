package com.traffbraza.funnycombination.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.traffbraza.funnycombination.db.HighScoreEntity
import com.traffbraza.funnycombination.db.HighScoresDao
import com.traffbraza.funnycombination.di.qualifiers.DispatcherIO
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
class SavingHighScoreWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val highScoresDao: HighScoresDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            val score = inputData.getInt(SCORE_KEY, -1)
            if (score == -1) return@withContext Result.failure()
            val isNewRecord = try {
                !highScoresDao.hasBetterPreviousResult(score)
            } catch (_: Exception) {
                return@withContext Result.failure()
            }
            if (isNewRecord) {
                val timestamp = inputData.getLong(TIMESTAMP_KEY, -1L)
                if (timestamp == -1L) return@withContext Result.failure()
                try {
                    highScoresDao.insertNewHighScore(
                        HighScoreEntity(
                            timestamp = timestamp,
                            score = score
                        )
                    )
                } catch (_: Exception) {
                    return@withContext Result.failure()
                }
            }
            return@withContext Result.success(workDataOf(IS_NEW_HIGH_SCORE_KEY to isNewRecord))
        }
    }

    companion object {
        const val SCORE_KEY = "SCORE_KEY"
        const val TIMESTAMP_KEY = "TIMESTAMP_KEY"
        const val IS_NEW_HIGH_SCORE_KEY = "IS_NEW_HIGH_SCORE_KEY"
    }
}
