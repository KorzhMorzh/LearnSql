package ru.learnsql.app_api

import kotlin.reflect.KClass

interface ApiProvider<T> {
    fun provide(): T
}

class ComponentApiException(message: String) : Exception(message)

interface ComponentApiFactory {
    fun <T : Any> provide(provider: (factory: ComponentApiFactory) -> ApiProvider<T>): T
    fun <T : Any> provideWithoutFactory(provider: () -> ApiProvider<T>): T
    fun <T : Any> providePersistable(cls: KClass<out T>, provider: (factory: ComponentApiFactory) -> ApiProvider<T>): T
    fun clearNonCriticalPersistable()
    operator fun <T : Any> get(kClass: KClass<out T>): T?
}