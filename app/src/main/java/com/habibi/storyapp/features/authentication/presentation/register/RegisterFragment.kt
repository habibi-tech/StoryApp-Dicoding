package com.habibi.storyapp.features.authentication.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.habibi.core.data.Resource
import com.habibi.core.utils.showSnackBar
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initEditTextChangeListener()
        initObserver()
    }

    private fun initEditTextChangeListener() {

        binding.edRegisterName.doOnTextChanged { _, _, _, _ ->
            checkFieldValidation()
        }

        binding.edRegisterEmail.doOnTextChanged { _, _, _, _ ->
            checkFieldValidation()
        }

        binding.edRegisterPassword.doOnTextChanged { _, _, _, _ ->
            checkFieldValidation()
        }

    }

    private fun checkFieldValidation() {
        viewModel.checkFieldValidation(
            binding.edWrapRegisterName.error,
            binding.edRegisterName.text.toString(),
            binding.edWrapRegisterEmail.error,
            binding.edRegisterEmail.text.toString(),
            binding.edWrapRegisterPassword.error,
            binding.edRegisterPassword.text.toString()
        )
    }

    private fun initObserver() {

        viewModel.fieldValid.observe(viewLifecycleOwner) {
            binding.btnRegisterSubmit.isEnabled = it
        }

        viewModel.register.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    onLoading(true)
                }
                is Resource.Success -> {
                    onSuccess(it.message)
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

    private fun initListener() {

        binding.btnRegisterSubmit.setOnClickListener {
            viewModel.postRegister(
                binding.edRegisterName.text.toString(),
                binding.edRegisterEmail.text.toString(),
                binding.edRegisterPassword.text.toString()
            )
        }

    }

    private fun onLoading(isLoading: Boolean) {
        binding.apply {
            edRegisterName.isEnabled = !isLoading
            edRegisterEmail.isEnabled = !isLoading
            edRegisterPassword.isEnabled = !isLoading
            if (isLoading) {
                btnRegisterSubmit.visibility = View.INVISIBLE
                pbRegister.visibility = View.VISIBLE
            } else {
                btnRegisterSubmit.visibility = View.VISIBLE
                pbRegister.visibility = View.GONE
            }
        }
    }

    private fun onSuccess(message: String) {

        requireActivity().showSnackBar(message)

        goToLogin()
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

    private fun goToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}