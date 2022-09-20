package com.habibi.storyapp.features.story.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.FragmentStoryListBinding
import com.habibi.storyapp.databinding.ItemStoryBinding
import dagger.hilt.android.AndroidEntryPoint

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

        setUpFabExtend()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.fabStoryList.setOnClickListener {
            findNavController().navigate(R.id.action_StoryListFragment_to_storyAddFragment)
        }

        binding.btnStoryListReload.setOnClickListener {
            viewModel.getListStory()
        }
    }

    private fun initObserver() {
        viewModel.listStory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    onLoading()
                }
                is Resource.Success -> {
                    onSuccess(it.data)
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

    private fun onLoading() {
        binding.apply {
            groupStoryListError.visibility = View.GONE
            rvStoryList.visibility = View.GONE
            pbStoryList.visibility = View.VISIBLE
            fabStoryList.visibility = View.GONE
        }
    }

    private fun onSuccess(listData: List<StoryItem>?) {

        binding.apply {
            groupStoryListError.visibility = View.GONE
            rvStoryList.visibility = View.VISIBLE
            pbStoryList.visibility = View.GONE
            fabStoryList.visibility = View.VISIBLE
        }

        listData?.let {
            if (it.isEmpty())
                onEmpty()
            else
                setUpRecyclerView(it)
        }
    }

    private fun onFailed(message: String) {
        binding.apply {
            groupStoryListError.visibility = View.VISIBLE
            rvStoryList.visibility = View.GONE
            pbStoryList.visibility = View.GONE
            fabStoryList.visibility = View.GONE

            tvStoryListMessageError.text = message
            ivStoryListImageError.setImageResource(R.drawable.ic_sentiment_very_dissatisfied)
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

    private fun setUpRecyclerView(listData: List<StoryItem>) {
        adapter = StoryListAdapter(listData) { itemData, itemBinding ->
            goToStoryDetail(itemData, itemBinding)
        }
        binding.rvStoryList.adapter = adapter
        binding.rvStoryList.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

    }

    private fun goToStoryDetail(item: StoryItem, itemStoryBinding: ItemStoryBinding) {
        val action = StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment()

        val extras = FragmentNavigatorExtras(
            itemStoryBinding.ivItemStoryImage to itemStoryBinding.ivItemStoryImage.transitionName,
            itemStoryBinding.tvItemStoryName to itemStoryBinding.tvItemStoryName.transitionName,
            itemStoryBinding.tvItemStoryDescription to itemStoryBinding.tvItemStoryDescription.transitionName
        )

        action.transitionImage = itemStoryBinding.ivItemStoryImage.transitionName
        action.transitionName = itemStoryBinding.tvItemStoryName.transitionName
        action.transitionDescription = itemStoryBinding.tvItemStoryDescription.transitionName
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