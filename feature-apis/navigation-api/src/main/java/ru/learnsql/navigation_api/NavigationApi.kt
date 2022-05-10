package ru.learnsql.navigation_api

import android.content.Context
import androidx.navigation.NavController

interface NavigationApi {
    fun openMainActivity(context: Context)
    fun openAuthorization(navController: NavController)
    fun openMainScreen(navController: NavController)
    fun openTaskModule(taskId: Int, id: Int, solution: String, taskNumber: Int, isResolved: Boolean, navController: NavController)
}