package com.habibi.storyapp.features.story.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.utils.setImage
import com.habibi.storyapp.databinding.ItemStoryBinding

class StoryListAdapter(
    private val onClick: (StoriesEntity, ItemStoryBinding) -> Unit
): PagingDataAdapter<StoriesEntity, StoryListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    inner class ViewHolder(
        private val binding: ItemStoryBinding,
        val onClick: (StoriesEntity, ItemStoryBinding) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        private var currentItem: StoriesEntity? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let {
                    onClick(it, binding)
                }
            }
        }

        fun bind(item: StoriesEntity?, position: Int){

            binding.ivItemPhoto.transitionName = "image$position"
            binding.tvItemName.transitionName = "name$position"
            binding.tvItemDescription.transitionName = "description$position"

            currentItem = item

            binding.apply {
                tvItemName.text = item?.name
                tvItemDescription.text = item?.description
            }

            binding.ivItemPhoto.setImage(item?.photoUrl)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoriesEntity>() {
            override fun areItemsTheSame(oldItem: StoriesEntity, newItem: StoriesEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoriesEntity, newItem: StoriesEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}