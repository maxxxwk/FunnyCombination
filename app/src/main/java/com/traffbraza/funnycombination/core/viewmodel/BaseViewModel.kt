package com.traffbraza.funnycombination.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {
    protected val mutableState = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = mutableState.asStateFlow()
}
