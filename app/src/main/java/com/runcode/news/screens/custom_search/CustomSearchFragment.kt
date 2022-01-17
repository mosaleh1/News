package com.runcode.news.screens.custom_search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.runcode.news.R
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.intents.CustomSearchIntent
import com.runcode.news.data.model.states.CustomSearchStates
import com.runcode.news.databinding.FragmentCustomSearchBinding
import com.runcode.news.screens.saved.SavedNewsAdapter
import com.runcode.news.screens.saved.SavedNewsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CustomSearchFragment : Fragment(), CustomSearchAdapter.Interaction {

    val viewModel: CustomSearchViewModel by viewModels()

    lateinit var binding: FragmentCustomSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomSearchBinding.bind(view)
        val query = CustomSearchFragmentArgs.fromBundle(requireArguments()).topic
        notifyQueryChanges(query)
        binding.submitSearchButton.setOnClickListener {
            notifyQueryChanges(
                binding.tftSearchCustom.text.toString()
            )
        }
    }

    private fun notifyQueryChanges(query: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.intentsChannel.send(CustomSearchIntent.GetNewsByQuery(query))
            setUpViews()
            viewModel.breakingNewsState.collect {
                when (it) {
                    is CustomSearchStates.Loading -> handleLoading()
                    is CustomSearchStates.Success -> handleSuccess(it.news)
                    is CustomSearchStates.Error -> handleError(it.message)

                }
            }
        }
    }

    private fun setUpViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handleLoading() {
        binding.barCustom.visibility = View.VISIBLE
    }

    private fun handleError(message: String?) {
        binding.barCustom.visibility = View.INVISIBLE
        binding.errorHandle.visibility = View.VISIBLE
    }

    private fun handleSuccess(news: List<BreakingNews>) {
        binding.barCustom.visibility = View.INVISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        val adapter = CustomSearchAdapter(this)
        Log.d(TAG, "handleSuccess: ${news.size}")
        adapter.submitList(news)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemSelected(position: Int, item: BreakingNews) {
        findNavController().navigate(
            CustomSearchFragmentDirections.actionCustomSearchFragmentToNewsProfileFragment(item)
        )
    }
}

private const val TAG = "CustomSearchFragment"