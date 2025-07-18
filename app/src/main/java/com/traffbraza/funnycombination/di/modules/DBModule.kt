package com.traffbraza.funnycombination.di.modules

import android.content.Context
import androidx.room.Room
import com.traffbraza.funnycombination.db.HighScoresDao
import com.traffbraza.funnycombination.db.HighScoresDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    @Singleton
    fun provideHighScoresDatabase(@ApplicationContext context: Context): HighScoresDatabase {
        return Room.databaseBuilder(
            context,
            HighScoresDatabase::class.java,
            "high_scores_db"
        ).build()
    }

    @Provides
    fun provideHighScoresDao(highScoresDatabase: HighScoresDatabase): HighScoresDao {
        return highScoresDatabase.getHighScoresDao()
    }
}
