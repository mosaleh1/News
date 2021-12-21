package com.runcode.news.screens.news_profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.runcode.news.R
import com.runcode.news.databinding.FragmentNewsProfileBinding


class NewsProfileFragment : Fragment() {

    lateinit var viewBinding: FragmentNewsProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentNewsProfileBinding.bind(view)
        val news = NewsProfileFragmentArgs.fromBundle(requireArguments()).breakingNews
        Glide
            .with(requireContext())
            .load(news.urlToImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .into(viewBinding.imgProfileHome)


        viewBinding.backIcon.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        viewBinding.apply {
            textContent.text = news.description
            titleProfile.text = news.title
            subTitleProfile.text = news.content
            titleProfileSecond.text = news.title
        }
        var isShow = true
        var scrollRange = -1
        viewBinding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                viewBinding.collapsingToolbarLayoutProfile.title = news.title
                viewBinding.backIcon.visibility = View.GONE
                isShow = true
            } else if (isShow) {
                viewBinding.collapsingToolbarLayoutProfile.title = " "
                     //careful there should a space between double quote otherwise it wont work
                isShow = false
                viewBinding.backIcon.visibility = View.VISIBLE
            }
        })
        viewBinding.visitWebSite.setOnClickListener {
            val websiteIntent = Intent()
            websiteIntent.action = Intent.ACTION_VIEW
            websiteIntent.data = Uri.parse(news.articleUrl?:"https//:www.google.com/")
            startActivity(websiteIntent)
        }
    }
}