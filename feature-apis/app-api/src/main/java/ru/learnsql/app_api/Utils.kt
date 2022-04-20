package ru.learnsql.app_api

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Fragment.getComponentApiFactory(): ComponentApiFactory {
    return (requireActivity().application as AppProvider).componentApiFactory
}

fun Activity.getComponentApiFactory(): ComponentApiFactory {
    return (application as AppProvider).componentApiFactory
}

val Activity.apiFactory: ComponentApiFactory
    get() = (application as AppProvider).componentApiFactory

val Fragment.apiFactory: ComponentApiFactory
    get() = (requireActivity().application as AppProvider).componentApiFactory

fun Service.getComponentApiFactory(): ComponentApiFactory {
    return (application as AppProvider).componentApiFactory
}

fun Fragment.getAppComponentApi(): AppComponentApi {
    return requireApi(getComponentApiFactory()[AppComponentApi::class])
}

fun AppCompatActivity.getAppComponentApi(): AppComponentApi {
    return requireApi(getComponentApiFactory()[AppComponentApi::class])
}

fun Activity.getAppComponentApi(): AppComponentApi {
    return requireApi(getComponentApiFactory()[AppComponentApi::class])
}

fun Service.getAppComponentApi(): AppComponentApi {
    return requireApi(getComponentApiFactory()[AppComponentApi::class])
}

fun FragmentActivity.getAppComponent(): AppComponentApi? {
    return (application as? AppProvider)?.componentApiFactory?.get(AppComponentApi::class)
}

fun Application.getAppComponent(): AppComponentApi {
    return requireApi((this as AppProvider).componentApiFactory[AppComponentApi::class])
}

fun <T : Any> requireApi(api: T?): T {
    return checkNotNull(api)
}