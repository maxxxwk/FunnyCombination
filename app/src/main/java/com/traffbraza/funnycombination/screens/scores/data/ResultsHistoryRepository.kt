package com.traffbraza.funnycombination.screens.scores.data

import com.traffbraza.funnycombination.db.HighScoresDao
import com.traffbraza.funnycombination.di.qualifiers.DispatcherIO
import com.traffbraza.funnycombination.screens.scores.ui.ResultsHistoryItem
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultsHistoryRepository @Inject constructor(
    private val highScoresDao: HighScoresDao,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getResultsHistoryFlow(): Flow<List<ResultsHistoryItem>> =
        highScoresDao.getHighScoresFlow().map { list ->
            list.map {
                ResultsHistoryItem(
                    score = it.score.toString(),
                    date = simpleDateFormat.format(Date(it.timestamp))
                )
            }
        }.flowOn(dispatcher)
}
