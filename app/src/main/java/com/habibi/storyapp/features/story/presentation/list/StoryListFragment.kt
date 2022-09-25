package com.habibi.storyapp.features.story.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.data.StoryRepository
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.FragmentStoryListBinding
import com.habibi.storyapp.databinding.ItemStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoryListFragment : Fragment() {

    private var _binding: FragmentStoryListBinding? = null
    private val binding get() = _binding!!

    private var adapter: StoryListAdapter? = null

    private val viewModel: StoryListViewModel by viewModels()

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
        layoutStateHandler()
        setUpFabExtend()
        initObserver()
        initListener()
    }

    private fun setUpRecyclerView() {
        adapter = StoryListAdapter { itemData, itemBinding ->
            goToStoryDetail(itemData, itemBinding)
        }

        binding.rvStoryList.adapter = adapter?.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter?.retry()
            }
        )
    }

    private fun layoutStateHandler() {
        lifecycleScope.launch {
            adapter?.loadStateFlow?.collect { loadState ->
                when {
                    loadState.source.refresh is LoadState.NotLoading && adapter?.itemCount == 0 -> {
                        onEmpty()
                    }
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading -> {
                        onSuccess()
                        if (adapter?.itemCount == StoryRepository.NETWORK_PAGE_SIZE) {
                            binding.rvStoryList.scrollToPosition(0)
                        }
                    }
                    loadState.source.refresh is LoadState.Loading -> {
                        onLoading()
                    }
                    loadState.source.refresh is LoadState.Error && adapter?.itemCount == 0 -> {
                        onError(R.string.failed_fetch_data)
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.fabStoryList.setOnClickListener {
            findNavController().navigate(R.id.action_StoryListFragment_to_storyAddFragment)
        }

        binding.btnStoryListReload.setOnClickListener {
            adapter?.retry()
        }
    }

    private fun initObserver() {
        viewModel.getListPaging.observe(viewLifecycleOwner) {
            adapter?.submitData(lifecycle, it)
        }
    }

    private fun onLoading() {
        binding.apply {
            groupStoryListError.visibility = View.GONE
            rvStoryList.visibility = View.GONE
            pbStoryList.visibility = View.VISIBLE
            fabStoryList.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.apply {
            groupStoryListError.visibility = View.GONE
            rvStoryList.visibility = View.VISIBLE
            pbStoryList.visibility = View.GONE
            fabStoryList.visibility = View.VISIBLE
        }
    }

    private fun onEmpty() {
        binding.apply {
            groupStoryListError.visibility = View.VISIBLE
            rvStoryList.visibility = View.GONE
            pbStoryList.visibility = View.GONE
            fabStoryList.visibility = View.GONE

            tvStoryListMessageError.text = getString(R.string.data_empty)
            ivStoryListImageError.setImageResource(R.drawable.ic_sentiment_dissatisfied)
        }
    }

    private fun onError(messageResource: Int) {
        binding.apply {
            groupStoryListError.visibility = View.VISIBLE
            rvStoryList.visibility = View.GONE
            pbStoryList.visibility = View.GONE
            fabStoryList.visibility = View.GONE

            tvStoryListMessageError.text = getString(messageResource)
            ivStoryListImageError.setImageResource(R.drawable.ic_sentiment_dissatisfied)
        }
    }

    private fun setUpFabExtend() {
        binding.rvStoryList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.fabStoryList.shrink()
                } else if (dy < 0) {
                    binding.fabStoryList.extend()
                }
            }
        })
    }

    private fun goToStoryDetail(item: StoriesEntity, itemStoryBinding: ItemStoryBinding) {
        val action = StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment()

        val extras = FragmentNavigatorExtras(
            itemStoryBinding.ivItemPhoto to itemStoryBinding.ivItemPhoto.transitionName,
            itemStoryBinding.tvItemName to itemStoryBinding.tvItemName.transitionName,
            itemStoryBinding.tvItemDescription to itemStoryBinding.tvItemDescription.transitionName
        )

        action.transitionImage = itemStoryBinding.ivItemPhoto.transitionName
        action.transitionName = itemStoryBinding.tvItemName.transitionName
        action.transitionDescription = itemStoryBinding.tvItemDescription.transitionName
        action.imageUrl = item.photoUrl
        action.name = item.name
        action.description = item.description
        findNavController().navigate(action, navigatorExtras =  extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}