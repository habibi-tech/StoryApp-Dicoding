package com.habibi.storyapp.features.story.presentation.add

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
import com.habibi.storyapp.databinding.FragmentStoryAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryAddFragment : Fragment() {

    private var _binding: FragmentStoryAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()
        initEditTextChangeListener()
    }

    private fun initEditTextChangeListener() {

        binding.edStoryAddDescription.doOnTextChanged { _, _, _, _ ->
            checkFieldValidation()
        }

    }

    private fun checkFieldValidation() {
        viewModel.checkFieldValidation(
            binding.edWrapStoryAddDescription.error,
            binding.edStoryAddDescription.text.toString()
        )
    }

    private fun initListener() {

        binding.btnStoryAddSubmit.setOnClickListener {
            viewModel.postNewStory(
                binding.edStoryAddDescription.text.toString()
            )
        }

    }

    private fun initObserver() {

        viewModel.fieldValid.observe(viewLifecycleOwner) {
            binding.btnStoryAddSubmit.isEnabled = it
        }

        viewModel.newStory.observe(viewLifecycleOwner) {
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

    private fun onLoading(isLoading: Boolean) {
        binding.apply {
            edStoryAddDescription.isEnabled = !isLoading
            if (isLoading) {
                btnStoryAddSubmit.visibility = View.INVISIBLE
                pbStoryAdd.visibility = View.VISIBLE
            } else {
                btnStoryAddSubmit.visibility = View.VISIBLE
                pbStoryAdd.visibility = View.GONE
            }
        }
    }

    private fun onSuccess(message: String) {

        requireActivity().showSnackBar(message)

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

    private fun goToStoryList() {
        findNavController().navigate(R.id.action_storyAddFragment_to_StoryListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}