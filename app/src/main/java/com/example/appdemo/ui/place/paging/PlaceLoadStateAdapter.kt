package com.example.appdemo.ui.place.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdemo.databinding.ItemStateBinding

class PlaceLoadStateAdapter : LoadStateAdapter<PlaceLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(val binding: ItemStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        with(holder) {
            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.errorMsg.isVisible = loadState !is LoadState.Loading

            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}