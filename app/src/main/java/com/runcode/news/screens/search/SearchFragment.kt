package com.runcode.news.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.runcode.news.R
import com.runcode.news.databinding.FragmentSearchBinding



class SearchFragment : Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    val binding
        get() =
            _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        val adapter: PagerAdapter = PagerAdapter(this)
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Health"
                1 -> "Sports"
                2 -> "Art"
                3 -> "Politics"
                4 -> "Food"
                5->"Other"
                else -> ""
            }
        }.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}
