package com.habibi.storyapp.features.story.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.habibi.storyapp.databinding.FragmentStoryDetailBinding
import com.habibi.core.utils.setImage

class StoryDetailFragment : Fragment() {

    private var _binding: FragmentStoryDetailBinding? = null
    private val binding get() = _binding!!

    private val args: StoryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryDetailBinding.inflate(inflater, container, false)

        binding.ivDetailPhoto.transitionName = args.transitionImage
        binding.tvDetailName.transitionName = args.transitionName
        binding.tvDetailDescription.transitionName = args.transitionDescription

        val moveAnimation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        sharedElementEnterTransition = moveAnimation
        sharedElementReturnTransition = moveAnimation

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivDetailPhoto.setImage(args.imageUrl)
        binding.tvDetailName.text = args.name
        binding.tvDetailDescription.text = args.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}