package com.runcode.news.screens.newsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.runcode.news.R
import com.runcode.news.databinding.FragmentNewsListBinding

import com.runcode.news.data.model.BreakingNews
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "NewsListFragment"

@AndroidEntryPoint
open class NewsListFragment(private val topic: String) : Fragment() {
    constructor() : this("")

    val viewModel:NewsListViewModel by viewModels()

    lateinit var adapter: NewsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsListBinding.bind(view)
        setUpRecyclerView(binding)
        viewModel.getNewsByTopic(topic)
        viewModel.getBreakingNewsByTopic.observe(viewLifecycleOwner, { data ->
            Log.d(TAG, "onViewCreated: ${data.size} ")
            binding.newsListBar.visibility = View.INVISIBLE
            adapter.setData(data)
        })
    }

    private fun setUpRecyclerView(binding: FragmentNewsListBinding) {
        binding.listItemsNewsList.layoutManager = LinearLayoutManager(requireContext())
        val emptyList = emptyList<BreakingNews>()
        adapter = NewsListAdapter(requireContext(), emptyList)
        binding.listItemsNewsList.adapter = adapter
    }
}