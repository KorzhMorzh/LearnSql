package ru.learnsql.course.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ViewModelModule
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.course.presentation.courseDetails.CourseDetailsFragment
import ru.learnsql.course.presentation.courses.CoursesFragment
import ru.learnsql.courses_api.CoursesApi
import ru.learnsql.navigation_api.NavigationApi

@Component(
    modules = [
        CourseModule::class,
        CourseBindsModule::class,
        ViewModelModule::class
    ]
)
internal interface CourseComponent {
    fun inject(coursesFragment: CoursesFragment)
    fun inject(coursesDetailsFragment: CourseDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi,
            @BindsInstance navigationApi: NavigationApi,
        ): CourseComponent
    }
}

@Component(
    modules = [
        CourseBindsModule::class
    ]
)
interface CourseExposeComponent : ApiProvider<CoursesApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): CourseExposeComponent
    }
}