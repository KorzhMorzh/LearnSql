package ru.learnsql.core

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel

open class BaseState<T, K>(
    open val navigationEvent: Event<T?> = Event(null),
    open val screenState: K
) {
    // override when adding additional fields
    open fun copy(
        screenState: K = this.screenState,
        navigationEvent: Event<T?> = this.navigationEvent
    ): BaseState<T, K> = BaseState(navigationEvent, screenState)
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