package ru.learnsql.course.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.learnsql.app_api.ViewModelKey
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.core.AssistedSavedStateViewModelFactory
import ru.learnsql.course.CourseApiImpl
import ru.learnsql.course.data.CoursesNetworkApi
import ru.learnsql.course.presentation.courseDetails.CourseDetailsViewModel
import ru.learnsql.course.presentation.courses.CoursesViewModel
import ru.learnsql.courses_api.CoursesApi

@Module
internal class CourseModule {
    @Provides
    fun coursesNetworkApi(retrofit: Retrofit): CoursesNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class CourseBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoursesViewModel::class)
    abstract fun provideCoursesVM(viewModel: CoursesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CourseDetailsViewModel::class)
    abstract fun provideCourseDetailsVM(factory: CourseDetailsViewModel.Factory): AssistedSavedStateViewModelFactory

    @Binds
    abstract fun provideCourseApi(courseApiImpl: CourseApiImpl): CoursesApi
}