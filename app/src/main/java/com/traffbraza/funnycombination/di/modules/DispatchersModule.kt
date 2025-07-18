package com.traffbraza.funnycombination.di.modules

import com.traffbraza.funnycombination.di.qualifiers.DispatcherDefault
import com.traffbraza.funnycombination.di.qualifiers.DispatcherIO
import com.traffbraza.funnycombination.di.qualifiers.DispatcherMain
import com.traffbraza.funnycombination.di.qualifiers.DispatcherUnconfined
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @DispatcherDefault
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @DispatcherMain
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DispatcherUnconfined
    fun provideDispatcherUnconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}
