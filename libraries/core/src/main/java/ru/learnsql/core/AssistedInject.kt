package ru.learnsql.core

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Reusable
import javax.inject.Inject

interface AssistedSavedStateViewModelFactory {
    fun create(savedStateHandle: SavedStateHandle): ViewModel
}

@Reusable
class InjectingSavedStateViewModelFactory @Inject constructor(
    private val assistedFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards AssistedSavedStateViewModelFactory>
) {
    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                try {
                    val it = assistedFactories.getValue(modelClass)
                    return modelClass.cast(it.create(handle))!!
                } catch (e: Exception) {
                    throw IllegalStateException("Unable to create a model for class $modelClass with key $key", e)
                }
            }
        }
    }
}