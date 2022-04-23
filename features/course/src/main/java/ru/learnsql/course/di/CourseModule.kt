package ru.learnsql.course.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.course.CourseApiImpl
import ru.learnsql.courses_api.CoursesApi

@Module
internal class CourseModule {
    //    @Provides
    //    fun authorizationNetworkApi(retrofit: Retrofit): AuthorizationNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class CourseBindsModule {
    //    @Binds
    //    @IntoMap
    //    @ViewModelKey(AuthorizationViewModel::class)
    //    abstract fun provideAuthorizationVM(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    abstract fun provideCourseApi(courseApiImpl: CourseApiImpl): CoursesApi
}