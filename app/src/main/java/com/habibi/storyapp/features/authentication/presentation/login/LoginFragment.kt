package com.habibi.storyapp.features.authentication.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import com.habibi.core.data.Resource
import com.habibi.core.utils.showSnackBar
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
        initEditTextChangeListener()
    }

    private fun initObserver() {

        viewModel.fieldValid.observe(viewLifecycleOwner) {
            binding.buttonLogin.isEnabled = it
        }

        viewModel.login.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    onLoading(true)
                }
                is Resource.Success -> {
                    onSuccess()
                }
                is Resource.Failed -> {
                    onFailed(it.message)
                }
                is Resource.Error -> {
                    onError(it.messageResource)
                }
            }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.apply {
            edLoginEmail.isEnabled = !isLoading
            edLoginPassword.isEnabled = !isLoading
            if (isLoading) {
                buttonLogin.visibility = View.INVISIBLE
                pbLogin.visibility = View.VISIBLE
            } else {
                buttonLogin.visibility = View.VISIBLE
                pbLogin.visibility = View.GONE
            }
        }
    }

    private fun onSuccess() {
        goToStoryList()
    }

    private fun onFailed(message: String) {
        onLoading(false)
        requireActivity().showSnackBar(message)
    }

    private fun onError(messageResource: Int) {
        onLoading(false)
        requireActivity().showSnackBar(
            getString(messageResource)
        )
    }

    private fun initEditTextChangeListener() {

        binding.edLoginEmail.doOnTextChanged { _, _, _, _ ->
            checkFieldValidation()
        }

        binding.edLoginPassword.doOnTextChanged { _, _, _, _ ->
            checkFieldValidation()
        }

    }

    private fun initListener() {

        binding.buttonLogin.setOnClickListener {
            viewModel.postLogin(
                binding.edLoginEmail.text.toString(),
                binding.edLoginPassword.text.toString()
            )
        }

        binding.tvLoginActionRegister.setOnClickListener {
            goToRegister()
        }

    }

    private fun checkFieldValidation() {
        viewModel.checkFieldValidation(
            binding.edWrapLoginEmail.error,
            binding.edLoginEmail.text.toString(),
            binding.edWrapLoginPassword.error,
            binding.edLoginPassword.text.toString()
        )
    }

    private fun goToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun goToStoryList() {
        val extras = ActivityNavigator.Extras.Builder()
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .build()
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToStoryActivity(), extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}