package ru.learnsql.course.presentation.courseDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.navigation.fragment.findNavController
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.back
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.DarkGray
import ru.learnsql.app_api.theme.Green
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.NonActiveGray
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.TopBar
import ru.learnsql.compose.Wrapper
import ru.learnsql.core.InjectingSavedStateViewModelFactory
import ru.learnsql.course.R
import ru.learnsql.course.R.drawable
import ru.learnsql.course.R.string
import ru.learnsql.course.di.DaggerCourseComponent
import ru.learnsql.course.domain.model.Task
import ru.learnsql.course.presentation.courseDetails.CourseDetailsNavigationEvent.OpenTaskDetails
import ru.learnsql.navigation_api.NavigationApi
import ru.learnsql.navigation_api.UPDATE_SCREEN_ON_BACK
import javax.inject.Inject

class CourseDetailsFragment : Fragment() {

    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory

    @Inject
    lateinit var navigationApi: NavigationApi

    override fun getDefaultViewModelProviderFactory(): Factory =
        abstractFactory.create(this, arguments)

    private val viewModel: CourseDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerCourseComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[AuthorizationApi::class]),
            requireApi(apiFactory[NavigationApi::class])
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ComposeView {
        setFragmentResultListener(UPDATE_SCREEN_ON_BACK) { _, _ ->
            viewModel.getCourseDetails()
            clearFragmentResult(UPDATE_SCREEN_ON_BACK)
        }
        return ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    val state = viewModel.state
                    when (val navEvent = state.value.navigationEvent.take()) {
                        is OpenTaskDetails -> {
                            navigationApi.openTaskModule(
                                navEvent.taskId,
                                navEvent.id,
                                navEvent.solution,
                                navEvent.taskNumber,
                                navEvent.isResolved,
                                findNavController()
                            )
                        }
                        null -> {}
                    }
                    val screenState = remember(key1 = state.value.screenState) {
                        state.value.screenState
                    }
                    val listState = rememberLazyListState()
                    Wrapper(
                        showLoader = screenState.loading,
                        showError = screenState.fail,
                        retryAction = viewModel::getCourseDetails
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Column(modifier = Modifier.background(BlueGradient)) {
                                TopBar(
                                    title = stringResource(string.my_courses_title),
                                    modifier = Modifier.background(Color.Transparent)
                                ) { back() }
                                Column(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(15.dp))
                                        .padding(horizontal = 25.dp)
                                        .fillMaxSize()
                                ) {
                                    Text(
                                        text = screenState.title,
                                        style = LearnSqlTheme.typography.h3,
                                        modifier = Modifier.padding(top = 12.dp)
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 14.dp)) {
                                        Icon(painter = painterResource(id = drawable.ic_task), contentDescription = "")
                                        Text(
                                            text = stringResource(id = string.course_program),
                                            style = LearnSqlTheme.typography.h4,
                                            modifier = Modifier.padding(start = 12.dp)
                                        )
                                        Text(
                                            text = resources.getQuantityString(
                                                R.plurals.tasks_completed_count,
                                                screenState.tasksCount,
                                                screenState.tasksResolved,
                                                screenState.tasksCount
                                            ),
                                            color = DarkGray,
                                            textAlign = TextAlign.End,
                                            style = LearnSqlTheme.typography.body2,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                    LinearProgressIndicator(
                                        progress = screenState.progress,
                                        color = Green,
                                        backgroundColor = NonActiveGray,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 21.dp)
                                    )
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        contentPadding = PaddingValues(bottom = 14.dp),
                                        modifier = Modifier.padding(top = 14.dp),
                                        state = listState,
                                    ) {
                                        itemsIndexed(screenState.tasks) { index, task ->
                                            TaskItem(task = task, index = index)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TaskItem(task: Task, index: Int) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.openTask(task, index + 1)
                }) {
            Text(
                text = stringResource(id = string.task_number, index + 1),
                color = if (task.isResolved) Green else Color.Black
            )
            if (task.isResolved) {
                Icon(
                    painter = painterResource(id = drawable.ic_done),
                    contentDescription = "", tint = Green
                )
            }
        }
    }

    companion object {
        internal const val COURSE_ID_ARG = "courseId"
        internal const val COURSE_TITLE_ARG = "courseTitle"
    }
}