package ru.learnsql.mobile.apiproviders

import androidx.annotation.GuardedBy
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiException
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.courses_api.CoursesApi
import ru.learnsql.methodic_api.MethodologyApi
import ru.learnsql.mobile.LearnSqlApp
import ru.learnsql.profile_api.ProfileApi
import ru.learnsql.settings_api.SettingsApi
import ru.learnsql.task_api.TaskApi
import kotlin.reflect.KClass

typealias ComponentBuilder<T> = () -> T

private val CRITICAL_APIS = setOf(AppComponentApi::class, AuthorizationApi::class)

fun LearnSqlApp.initComponentFactory() {
    componentApiFactory = object : ComponentApiFactory {
        /**
         * Map used for long living components
         */
        @GuardedBy("this")
        private val persistableMap: MutableMap<KClass<*>, Any> = mutableMapOf()

        /**
         * Globally registered components
         */
        private val componentMap: Map<KClass<*>, ComponentBuilder<Any>> = mapOf(
            AppComponentApi::class to { appComponentApi },
            AuthorizationApi::class to { providePersistable(AuthorizationApi::class, ::provideAuthorizationApi) },
            CoursesApi::class to { provide(::provideCourseApi) },
            MethodologyApi::class to { provide(::provideMethodologyApi) },
            ProfileApi::class to { provide(::provideProfileApi) },
            SettingsApi::class to { provide(::provideSettingsApi) },
            TaskApi::class to { provide(::provideTaskApi) },
        )

        /**
         * Allows to retrieve values by index
         * Example: requireApi(factory[ClassNameApi::class])
         * */
        override operator fun <T : Any> get(kClass: KClass<out T>): T? {
            @Suppress("UNCHECKED_CAST")
            val api = (componentMap[kClass] as? ComponentBuilder<T>)?.invoke()
                ?: throw ComponentApiException("Component not found for given $kClass")
            return api
        }

        /**
         * Provides component on demand without saving state
         * */
        override fun <T : Any> provide(provider: (factory: ComponentApiFactory) -> ApiProvider<T>): T {
            return provider.invoke(this).provide()
        }

        /**
         * Provides component and save to persistableMap
         * Note: Working with core api components
         * */
        @Synchronized
        override fun <T : Any> providePersistable(
            cls: KClass<out T>,
            provider: (factory: ComponentApiFactory) -> ApiProvider<T>
        ): T {
            return getPersistable(cls) ?: provider.invoke(this).provide()
                .also { putPersistable(cls, it) }
        }

        @Synchronized
        private fun <T : Any> putPersistable(cls: KClass<*>, provider: T): T {
            return provider.also { persistableMap[cls] = provider }
        }

        @Synchronized
        private fun <T : Any> getPersistable(cls: KClass<*>): T? {
            @Suppress("UNCHECKED_CAST")
            return persistableMap[cls] as? T
        }

        @Synchronized
        override fun clearNonCriticalPersistable() {
            persistableMap.keys.removeAll { it !in CRITICAL_APIS }
        }
    }
}