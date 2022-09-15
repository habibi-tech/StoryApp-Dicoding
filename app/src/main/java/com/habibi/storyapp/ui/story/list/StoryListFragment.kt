package com.habibi.storyapp.ui.story.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.habibi.storyapp.R
import com.habibi.storyapp.data.model.ItemStory
import com.habibi.storyapp.databinding.FragmentStoryListBinding
import com.habibi.storyapp.databinding.ItemStoryBinding

class StoryListFragment : Fragment() {

    private var _binding: FragmentStoryListBinding? = null
    private val binding get() = _binding!!

    private var adapter: StoryListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStoryListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_StoryListFragment_to_storyAddFragment)
        }
    }

    private fun setUpRecyclerView() {
        adapter = StoryListAdapter(data) { itemData, itemBinding ->
            goToStoryDetail(itemData, itemBinding)
        }
        binding.rvStoryList.adapter = adapter
        binding.rvStoryList.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

    }

    private fun goToStoryDetail(item: ItemStory, itemStoryBinding: ItemStoryBinding) {
        val action = StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment()

        val extras = FragmentNavigatorExtras(
            itemStoryBinding.ivItemStoryImage to itemStoryBinding.ivItemStoryImage.transitionName,
            itemStoryBinding.tvItemStoryName to itemStoryBinding.tvItemStoryName.transitionName,
            itemStoryBinding.tvItemStoryDescription to itemStoryBinding.tvItemStoryDescription.transitionName
        )

        action.transitionImage = itemStoryBinding.ivItemStoryImage.transitionName
        action.transitionName = itemStoryBinding.tvItemStoryName.transitionName
        action.transitionDescription = itemStoryBinding.tvItemStoryDescription.transitionName
        action.imageUrl = item.imageUrl
        action.name = item.name
        action.description = item.description
        findNavController().navigate(action, navigatorExtras =  extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private val data = arrayListOf(ItemStory(), ItemStory(), ItemStory(), ItemStory(), ItemStory())
    }
}