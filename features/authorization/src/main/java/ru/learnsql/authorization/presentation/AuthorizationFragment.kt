package ru.learnsql.authorization.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.authorization.di.DaggerAuthorizationComponent
import javax.inject.Inject

class AuthorizationFragment : Fragment() {
    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthorizationViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAuthorizationComponent.factory().create(getAppComponentApi()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column {
                        TextField(value = viewModel.username.value, onValueChange = { viewModel.onUsernameChange(it) })
                        TextField(value = viewModel.password.value, onValueChange = { viewModel.onPasswordChange(it) })
                        Button(onClick = { viewModel.login() }) {
                            Text(text = "Login")
                        }
                    }

                }
            }
        }
    }
}
