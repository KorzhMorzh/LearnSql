package ru.learnsql.mobile.state

import android.content.Context
import com.google.gson.Gson
import ru.learnsql.app_api.state.StateProvider
import javax.inject.Inject

class StateProviderImpl @Inject constructor(
    val context: Context,
    gson: Gson
) : StateProvider {
    override val token = TokenStateImpl(context)

    override val userInfo = UserInfoStateImpl(context, gson)

    override fun clearStateInfo() {
        token.clear()
        userInfo.clear()
    }
}