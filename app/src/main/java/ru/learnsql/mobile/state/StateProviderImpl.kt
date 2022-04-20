package ru.learnsql.mobile.state

import android.content.Context
import ru.learnsql.app_api.state.StateProvider
import javax.inject.Inject

class StateProviderImpl @Inject constructor(
    val context: Context
) : StateProvider {
    override val token = TokenStateImpl(context)

    override val userInfo = UserInfoStateImpl(context, token)

    override fun clearStateInfo() {
        token.clear()
    }
}