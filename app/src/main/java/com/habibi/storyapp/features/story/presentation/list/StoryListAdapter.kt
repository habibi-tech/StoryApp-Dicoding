package com.habibi.storyapp.features.story.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.utils.setImage
import com.habibi.storyapp.databinding.ItemStoryBinding

class StoryListAdapter(
    private val data: List<StoryItem>,
    private val onClick: (StoryItem, ItemStoryBinding) -> Unit
): RecyclerView.Adapter<StoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int =
        data.size

    inner class ViewHolder(
        private val binding: ItemStoryBinding,
        val onClick: (StoryItem, ItemStoryBinding) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        private var currentItem: StoryItem? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let {
                    onClick(it, binding)
                }
            }
        }

        fun bind(item: StoryItem, position: Int){

            binding.ivItemStoryImage.transitionName = "image$position"
            binding.tvItemStoryName.transitionName = "name$position"
            binding.tvItemStoryDescription.transitionName = "description$position"

            currentItem = item

            binding.apply {
                tvItemStoryName.text = item.name
                tvItemStoryDescription.text = item.description
            }

            binding.ivItemStoryImage.setImage(item.photoUrl)
        }
    }

}