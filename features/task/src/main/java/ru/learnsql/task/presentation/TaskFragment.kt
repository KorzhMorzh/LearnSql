package ru.learnsql.task.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.navigation.fragment.findNavController
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.back
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.Blue
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.Green
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.LightBlue
import ru.learnsql.app_api.theme.LightGray
import ru.learnsql.app_api.theme.NonActiveGray
import ru.learnsql.app_api.theme.Red
import ru.learnsql.app_api.theme.WhiteBlue
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.AccentButton
import ru.learnsql.compose.TopBar
import ru.learnsql.compose.Wrapper
import ru.learnsql.compose.gridItems
import ru.learnsql.core.InjectingSavedStateViewModelFactory
import ru.learnsql.navigation_api.UPDATE_SCREEN_ON_BACK
import ru.learnsql.task.R
import ru.learnsql.task.R.drawable
import ru.learnsql.task.R.string
import ru.learnsql.task.data.dto.TaskStatus.ERROR
import ru.learnsql.task.data.dto.TaskStatus.OK
import ru.learnsql.task.di.DaggerTaskComponent
import ru.learnsql.task.presentation.TaskNavigationEvent.OpenDatabaseDescription
import ru.learnsql.task.presentation.TaskNavigationEvent.OpenDatabaseImage
import ru.learnsql.task.presentation.database.DATABASE_DESCRIPTION_NAV_ARG
import ru.learnsql.task.presentation.database.DATABASE_IMAGE_NAV_ARG
import javax.inject.Inject

const val TASK_ID_ARG = "taskId"
const val ID_ARG = "id"
const val IS_RESOLVED_ARG = "isResolved"
const val SOLUTION_ARG = "solution"
const val TASK_NUMBER_ARG = "taskNumber"

class TaskFragment : Fragment() {
    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory

    override fun getDefaultViewModelProviderFactory(): Factory =
        abstractFactory.create(this, arguments)

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerTaskComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[AuthorizationApi::class])
        ).inject(this)
        setFragmentResult(UPDATE_SCREEN_ON_BACK, bundleOf())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    val state = viewModel.state
                    when (val navEvent = state.value.navigationEvent.take()) {
                        is OpenDatabaseDescription -> {
                            findNavController().navigate(
                                R.id.toDatabaseDescriptionFragment, bundleOf(
                                    DATABASE_DESCRIPTION_NAV_ARG to navEvent.databaseDescription
                                )
                            )
                        }
                        is OpenDatabaseImage -> {
                            findNavController().navigate(
                                R.id.toDatabaseImageFragment, bundleOf(
                                    DATABASE_IMAGE_NAV_ARG to navEvent.databaseImage
                                )
                            )
                        }
                        null -> {}
                    }
                    val screenState = remember(key1 = state.value.screenState) {
                        state.value.screenState
                    }
                    Wrapper(
                        showLoader = screenState.loading,
                        showError = screenState.fail,
                        retryAction = viewModel::loadTask
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Column(modifier = Modifier.background(BlueGradient)) {
                                TopBar(
                                    title = stringResource(R.string.task_number, screenState.taskNumber),
                                    modifier = Modifier.background(Color.Transparent)
                                ) { back() }
                                LazyColumn(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(15.dp))
                                        .padding(horizontal = 25.dp)
                                        .fillMaxSize(),
                                    contentPadding = PaddingValues(bottom = 12.dp)
                                ) {
                                    item {
                                        Task(screenState = screenState)
                                    }
                                    if (!screenState.expectedResult.isNullOrEmpty()) {
                                        resultGrid(result = screenState.expectedResult, isStudentResult = false)
                                    }
                                    if (!screenState.studentResult.isNullOrEmpty()) {
                                        resultGrid(result = screenState.studentResult, isStudentResult = true)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    @Composable
    private fun Task(screenState: TaskScreenState) {
        Column {
            if (screenState.hasDatabaseDescription || screenState.hasDatabaseImage){
                Text(
                    text = stringResource(id = string.database),
                    style = LearnSqlTheme.typography.h4,
                    modifier = Modifier.padding(top = 14.dp).align(CenterHorizontally),
                    color = Color.Black
                )
            }
            if (screenState.hasDatabaseDescription) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable { viewModel.openDatabaseDescription() }
                ) {
                    Icon(painter = painterResource(id = drawable.ic_database), contentDescription = "", tint = LightBlue)
                    Text(
                        text = stringResource(id = string.database_description),
                        style = LearnSqlTheme.typography.body2,
                        color = Blue,
                        modifier = Modifier.padding(start = 3.dp).weight(1f),
                    )
                    Icon(
                        painter = painterResource(id = ru.learnsql.compose.R.drawable.ic_crop_right_arrow),
                        contentDescription = "",
                        tint = LightBlue,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
            if (screenState.hasDatabaseImage) {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .background(WhiteBlue, RoundedCornerShape(5.dp))
                        .width(116.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .clickable {
                            viewModel.openDatabaseImage()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = string.attachment),
                        style = LearnSqlTheme.typography.body2,
                        color = LightBlue
                    )
                }
            }
            Text(
                text = stringResource(id = string.task),
                style = LearnSqlTheme.typography.h4,
                modifier = Modifier.padding(top = 14.dp).align(CenterHorizontally),
                color = Color.Black
            )
            Text(
                text = screenState.taskText,
                style = LearnSqlTheme.typography.body1,
                modifier = Modifier.padding(top = 12.dp),
                color = Color.Black
            )
            OutlinedTextField(
                value = screenState.solution,
                onValueChange = viewModel::onSolutionChange, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(LightGray, RoundedCornerShape(8.dp))
                    .defaultMinSize(minHeight = 125.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = LightBlue,
                    unfocusedBorderColor = if (screenState.status == OK) Green else NonActiveGray,
                    errorBorderColor = Red,
                    textColor = Color.Black,
                ),
                enabled = screenState.status != OK,
                isError = screenState.status == ERROR,
                shape = RoundedCornerShape(8.dp)
            )
            if (screenState.status != OK) {
                AccentButton(
                    text = string.do_task,
                    isEnabled = screenState.isButtonEnabled,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 12.dp)
                        .defaultMinSize(minHeight = 32.dp)
                        .align(Alignment.End)
                ) {
                    viewModel.doTask()
                }
            }
            when (screenState.status) {
                OK -> {
                    Text(
                        text = stringResource(id = string.correct),
                        color = Green,
                        style = LearnSqlTheme.typography.h4,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
                ERROR -> {
                    Text(
                        text = stringResource(id = string.incorrect),
                        color = Red,
                        style = LearnSqlTheme.typography.h4,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    if (screenState.message != null) {
                        Text(
                            text = screenState.message,
                            color = Color.Black,
                            style = LearnSqlTheme.typography.body1,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
                null -> {}
            }
        }
    }

    private fun LazyListScope.resultGrid(result: List<List<Any>>, isStudentResult: Boolean) {
        item {
            Text(
                text = stringResource(id = if (isStudentResult) string.student_result else string.expected_result),
                color = Color.Black,
                style = LearnSqlTheme.typography.h4,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        gridItems(
            data = result.flatten(),
            nColumns = result.first().size,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = it.toString(),
                color = Color.Black,
                style = LearnSqlTheme.typography.body1,
                modifier = Modifier.padding(top = 17.dp)
            )
        }
    }
}