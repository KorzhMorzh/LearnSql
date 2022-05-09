package ru.learnsql.course.presentation.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.DarkGray
import ru.learnsql.app_api.theme.DisabledBlue
import ru.learnsql.app_api.theme.DisabledLightBlue
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.LightBlue
import ru.learnsql.app_api.theme.Yellow
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.Wrapper
import ru.learnsql.course.R
import ru.learnsql.course.R.drawable
import ru.learnsql.course.R.string
import ru.learnsql.course.di.DaggerCourseComponent
import ru.learnsql.course.domain.model.Course
import ru.learnsql.course.presentation.courseDetails.CourseDetailsFragment
import ru.learnsql.course.presentation.courses.CoursesNavigationEvent.OpenCourseDetails
import ru.learnsql.course.presentation.courses.TabItem.AllCourses
import ru.learnsql.course.presentation.courses.TabItem.MyCourses
import javax.inject.Inject

sealed class TabItem(@StringRes val title: Int, val index: Int) {
    object AllCourses : TabItem(string.all_courses_title, 0)
    object MyCourses : TabItem(string.my_courses_title, 1)
}

internal class CoursesFragment : Fragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CoursesViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerCourseComponent.factory().create(getAppComponentApi(), requireApi(apiFactory[AuthorizationApi::class]))
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(requireContext()).apply {
        setContent {
            LearnSqlTheme {
                val state = viewModel.state
                val screenState = remember(key1 = state.value.screenState) {
                    state.value.screenState
                }
                when (val navEvent = state.value.navigationEvent.take()) {
                    is OpenCourseDetails -> {
                        findNavController().navigate(
                            R.id.toCourseDetailsFragment, bundleOf(
                                CourseDetailsFragment.COURSE_ID_ARG to navEvent.courseId,
                                CourseDetailsFragment.COURSE_TITLE_ARG to navEvent.title,
                            )
                        )
                    }
                    else -> {}
                }
                Wrapper(
                    showLoader = screenState.loading,
                    showError = screenState.fail,
                    retryAction = viewModel::getCourses
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .background(BlueGradient)
                                .padding(horizontal = 25.dp)
                        ) {
                            CoursesTabs(screenState)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CoursesTabs(screenState: CoursesScreenState) {
        TabRow(
            selectedTabIndex = screenState.selectedTab,
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
            indicator = {}
        ) {
            listOf(AllCourses, MyCourses).forEachIndexed { index, tab ->
                val selected = screenState.selectedTab == index
                Tab(
                    modifier = Modifier
                        .clip(
                            if (index == AllCourses.index) {
                                RoundedCornerShape(bottomStart = 10.dp, topStart = 10.dp)
                            } else {
                                RoundedCornerShape(bottomEnd = 10.dp, topEnd = 10.dp)
                            }
                        )
                        .background(if (selected) LightBlue else DisabledBlue),
                    selected = selected,
                    onClick = {
                        viewModel.selectTab(index)
                    },
                    text = {
                        Text(
                            text = stringResource(id = tab.title),
                            color = if (selected) Color.White else DisabledLightBlue,
                            style = LearnSqlTheme.typography.body2
                        )
                    }
                )
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier.padding(top = 25.dp)
        ) {
            val courses = if (screenState.selectedTab == AllCourses.index) screenState.allCourses else screenState.myCourses
            items(courses) { course ->
                CourseItem(course = course)
            }
        }
    }

    @Composable
    fun CourseItem(course: Course) {
        Column(
            Modifier
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding()
                .clickable(enabled = course.isMy) { viewModel.openCourseDetails(course) }
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = course.title,
                color = Color.Black,
                style = LearnSqlTheme.typography.h4
            )
            Row(Modifier.padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = string.difficulty),
                    color = DarkGray,
                    style = LearnSqlTheme.typography.body2,
                    modifier = Modifier.padding(end = 8.dp)
                )
                for (i in 1..5) {
                    if (i <= course.difficulty) {
                        Icon(painter = painterResource(id = drawable.ic_filled_star), contentDescription = "", tint = Yellow)
                    } else {
                        Icon(painter = painterResource(id = drawable.ic_empty_star), contentDescription = "", tint = Yellow)
                    }
                }
            }
            if (!course.isMy) {
                Text(
                    text = stringResource(id = R.string.enroll_course),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { viewModel.enrollCourse(course.id) },
                    color = LearnSqlTheme.colors.secondary,
                    style = LearnSqlTheme.typography.body1
                )
            }
        }
    }
}