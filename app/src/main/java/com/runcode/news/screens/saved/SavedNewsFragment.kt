package com.runcode.news.screens.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.runcode.news.R
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.intents.SavedNewsIntent
import com.runcode.news.data.model.states.SavedNewsStates
import com.runcode.news.databinding.FragmentSavedNewsBinding
import com.runcode.news.screens.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SavedNewsFragment : Fragment(), SavedNewsAdapter.Interaction {

    lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: SavedNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)
        setUpViews()
        lifecycleScope.launchWhenCreated {
            viewModel.intentsChannel.send(SavedNewsIntent.GetAllFavorite)

            viewModel.breakingNewsState.collect {
                when (it) {
                    is SavedNewsStates.Loading -> handleLoading()
                    is SavedNewsStates.Success -> handleSuccess(it.news)
                    is SavedNewsStates.Error -> handleError(it.message)

                }
            }
        }


    }

    private fun setUpViews() {
        binding.recyclerSavedNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handleLoading() {
        binding.bar.visibility = View.VISIBLE
    }

    private fun handleError(message: String?) {
        binding.bar.visibility = View.INVISIBLE
    }
    private fun handleSuccess(news: List<BreakingNews>) {
        binding.bar.visibility = View.INVISIBLE
        val adapter : SavedNewsAdapter = SavedNewsAdapter(this)
        adapter.submitList(news)
        binding.recyclerSavedNews.adapter = adapter
        Log.d(TAG, "handleSuccess: ${news.size}")
    }

    override fun onItemSelected(position: Int, item: BreakingNews) {
        findNavController().navigate(
            SavedNewsFragmentDirections.actionSavedNewsFragmentToNewsProfileFragment(item)
        )
    }
}

private const val TAG = "SavedNewsFragment"