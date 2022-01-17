package com.runcode.news.screens.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        binding.tftSearchText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.submitBtn.performClick();
                    return true;
                }
                return false;
            }
        })

        binding.submitBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                binding.tftSearchText.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToCustomSearchFragment(
                    binding.tftSearchText.text.toString()
                )
            )
        }

        val adapter: PagerAdapter = PagerAdapter(this)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Health"
                1 -> "Sports"
                2 -> "Art"
                3 -> "Politics"
                4 -> "Food"
                5 -> "Other"
                else -> ""
            }
        }.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}
