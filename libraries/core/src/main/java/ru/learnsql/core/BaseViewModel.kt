package ru.learnsql.core

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel

abstract class BaseState<T, K>(
    open val navigationEvent: Event<T?> = Event(null),
    open val screenState: K
) {
    fun copy(
        screenState: K = this.screenState,
        navigationEvent: Event<T?> = this.navigationEvent
    ): BaseState<T, K> = object : BaseState<T, K>(navigationEvent, screenState) {}
}

abstract class BaseViewModel<T, K> : ViewModel() {
    abstract var state: MutableState<BaseState<T, K>>
        protected set

    protected fun updateScreen(block: K.() -> K) {
        state.value = state.value.copy(
            screenState = block(state.value.screenState)
        )
    }
}