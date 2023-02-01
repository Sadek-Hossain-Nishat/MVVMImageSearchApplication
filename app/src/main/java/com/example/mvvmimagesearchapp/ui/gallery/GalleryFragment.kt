package com.example.mvvmimagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mvvmimagesearchapp.R
import com.example.mvvmimagesearchapp.data.UnsplashPhoto
import com.example.mvvmimagesearchapp.databinding.FragmentGalleryBinding
import com.example.mvvmimagesearchapp.ui.gallery.UnsplashPhotoAdapter.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) ,MenuProvider, OnItemClickListener{


    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!

    lateinit var menuHost:MenuHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        menuHost = requireActivity()

        val adapter = UnsplashPhotoAdapter(this)

        menuHost.addMenuProvider(this)

        binding.apply {

            idRecyclerView.setHasFixedSize(true)
            idRecyclerView.itemAnimator = null
            idRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter {
                    adapter.retry()
                },

                footer = UnsplashPhotoLoadStateAdapter {

                    adapter.retry()

                },


                )
            idButtonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {

            adapter.submitData(viewLifecycleOwner.lifecycle, it)


        }


        adapter.addLoadStateListener {
            loadState ->
            binding.apply {
                idProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                idRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                idButtonRetry.isVisible = loadState.source.refresh is LoadState.Error
                idTextViewError.isVisible = loadState.source.refresh is LoadState.Error


                // empty view

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {

                    idRecyclerView.isVisible = false
                    idTextViewEmpty.isVisible = true

                } else {

                    idTextViewEmpty.isVisible = false
                    
                }

            }
        }



    }


  /***  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery,menu)

        val searchItem  = menu.findItem(R.id.id_action_search)
        val searchView  = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null) {
                    binding.idRecyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })
        
    }
  ***/

    override fun onDestroyView() {
        super.onDestroyView()
      menuHost.removeMenuProvider(this)
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_gallery,menu)

        val searchItem  = menu.findItem(R.id.id_action_search)
        val searchView  = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null) {
                    binding.idRecyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    override fun onItemClick(photo: UnsplashPhoto) {


        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)

    }

}