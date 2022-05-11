package ru.learnsql.navigation

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import ru.learnsql.navigation_api.NavigationApi
import ru.learnsql.task.presentation.ID_ARG
import ru.learnsql.task.presentation.IS_RESOLVED_ARG
import ru.learnsql.task.presentation.SOLUTION_ARG
import ru.learnsql.task.presentation.TASK_ID_ARG
import ru.learnsql.task.presentation.TASK_NUMBER_ARG
import javax.inject.Inject

class NavigationApiImpl @Inject internal constructor() : NavigationApi {
    override fun openMainActivity(context: Context) {
        context.startActivity(Intent(context, NavigationActivity::class.java))
    }

    override fun openAuthorization(navController: NavController) {
        navController.navigate(R.id.toAuthorizationFragment)
    }

    override fun openMainScreen(navController: NavController) {
        navController.navigate(R.id.toMainScreen)
    }

    override fun openTaskModule(taskId: Int, id: Int, solution: String, taskNumber: Int, isResolved: Boolean, navController: NavController) {
        navController.navigate(
            R.id.toTaskDetailFragment, bundleOf(
                TASK_ID_ARG to taskId,
                ID_ARG to id,
                SOLUTION_ARG to solution,
                TASK_NUMBER_ARG to taskNumber,
                IS_RESOLVED_ARG to isResolved
            )
        )
    }

    override fun openProfileModule(navController: NavController) {
        navController.navigate(R.id.toProfileFragment)
    }
}