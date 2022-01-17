package com.runcode.news.screens.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.runcode.news.screens.newsList.NewsListFragment

class PagerAdapter(fm: Fragment) : FragmentStateAdapter(
    fm
) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0 -> NewsListFragment("Health")
            1 -> NewsListFragment("Sports")
            2 -> NewsListFragment("Art")
            3 -> NewsListFragment("Politics")
            4 -> NewsListFragment("Food")
           else -> NewsListFragment()
       }
    }
}