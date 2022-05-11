package ru.learnsql.profile.di

import androidx.compose.material.ExperimentalMaterialApi
import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ViewModelModule
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.profile.presentation.ProfileFragment
import ru.learnsql.profile.presentation.editpassword.EditPasswordFragment
import ru.learnsql.profile_api.ProfileApi

@Component(
    modules = [
        ProfileModule::class,
        ProfileBindsModule::class,
        ViewModelModule::class
    ]
)
internal interface ProfileComponent {
    @OptIn(ExperimentalMaterialApi::class)
    fun inject(profileFragment: ProfileFragment)
    fun inject(profileFragment: EditPasswordFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): ProfileComponent
    }
}

@Component(
    modules = [
        ProfileBindsModule::class
    ]
)
interface ProfileExposeComponent : ApiProvider<ProfileApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): ProfileExposeComponent
    }
}