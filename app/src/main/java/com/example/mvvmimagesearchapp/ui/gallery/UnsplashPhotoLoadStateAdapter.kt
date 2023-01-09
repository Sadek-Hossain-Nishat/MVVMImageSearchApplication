package com.example.mvvmimagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmimagesearchapp.databinding.UnsplashPhotoLoadStateFooterBinding

class UnsplashPhotoLoadStateAdapter(private val retry:()->Unit):LoadStateAdapter<UnsplashPhotoLoadStateAdapter.LoadStateViewHolder>() {




    inner class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.idButtonRetry.setOnClickListener {
                retry.invoke()



            }
        }

        fun bind(loadState:LoadState) {
            binding.apply {
                idProgressBar.isVisible = loadState is LoadState.Loading
                idButtonRetry.isVisible = loadState !is LoadState.Loading
                idTextViewError.isVisible = loadState !is LoadState.Loading
            }



        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
       holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnsplashPhotoLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)

        return LoadStateViewHolder(binding)
    }
}