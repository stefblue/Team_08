package com.swt.augmentmycampus.ui.settings

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.swt.augmentmycampus.AsyncRequest
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.network.LoginRequest
import com.swt.augmentmycampus.network.UserInformationResponse
import com.swt.augmentmycampus.network.Webservice
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var webService: Webservice;

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val username = root.findViewById(R.id.fragment_login_user) as EditText
        val password = root.findViewById(R.id.fragment_login_password) as EditText

        val button = root.findViewById(R.id.fragment_login_submit) as Button
        button.setOnClickListener {

            val loginResponse = AsyncRequest<UserInformationResponse>().execute(webService.login(LoginRequest(username.text.toString(),password.text.toString())))

            if (loginResponse.get() != null) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_settings_user_container, UserInformationFragment(loginResponse.get()?.givenName!!, loginResponse.get()?.lastName!!))
                    .commit()
            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(R.string.invalid_credentials)
                builder.setPositiveButton(R.string.ok, null)
                builder.show()
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}