package com.habibi.storyapp.features.story.presentation.add

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.habibi.core.data.Resource
import com.habibi.core.utils.createCustomTempFile
import com.habibi.core.utils.showSnackBar
import com.habibi.core.utils.toFile
import com.habibi.storyapp.BuildConfig
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.FragmentStoryAddBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class StoryAddFragment : Fragment() {

    private var _binding: FragmentStoryAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryAddViewModel by viewModels()

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = selectedImg.toFile(requireActivity())
            checkFieldValidation(myFile)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val myFile = File(viewModel.currentPhotoPath)
            checkFieldValidation(myFile)
        }
    }

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

    private fun checkFieldValidation(file: File? = null) {
        viewModel.checkFieldValidation(
            file,
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

        binding.btnStoryAddGallery.setOnClickListener {
            goToGallery()
        }

        binding.btnStoryAddCamera.setOnClickListener {
            goToCamera()
        }
    }

    private fun initObserver() {

        viewModel.photoFile.observe(viewLifecycleOwner) {
            binding.imgStoryAddPreview.setImageBitmap(
                BitmapFactory.decodeFile(it.path)
            )
        }

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
            btnStoryAddCamera.isEnabled = !isLoading
            btnStoryAddGallery.isEnabled = !isLoading
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

    private fun goToGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun goToCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireActivity()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireActivity(),
                BuildConfig.APPLICATION_ID,
                it
            )
            viewModel.setCurrentPhotoPath(it.absolutePath)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}