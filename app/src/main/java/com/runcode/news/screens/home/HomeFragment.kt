package com.runcode.news.screens.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.runcode.news.R
import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.Headlines
import com.runcode.news.data.model.intents.HomeIntent
import com.runcode.news.data.model.states.HomeStates
import com.runcode.news.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "HomeFragment"

@AndroidEntryPoint
@InternalCoroutinesApi
class HomeFragment : Fragment(), NewsAdapter.OnNewsItemListener {
//    private var _binding: FragmentHomeBinding? = null
//    private val binding: FragmentHomeBinding
//        get() = _binding!!


    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding: FragmentHomeBinding = FragmentHomeBinding.bind(view)
        // _binding = FragmentHomeBinding.bind(view)

        adapter.clickListener = this@HomeFragment

        setUpDataToHeadline(binding)

        setUpRecyclerView(binding)

    }

    private fun setUpDataToHeadline(binding: FragmentHomeBinding) {
        lifecycleScope.launchWhenCreated {
            viewModel.intentsChannel.send(HomeIntent.GetHeadlines)
            viewModel.headlinesState.collect {

                when (it) {

                    is HomeStates.Idle -> {
                        Log.d(TAG, "setUpDataToHeadline: idle")
                    }

                    is HomeStates.Loading -> {
                        Log.d(TAG, "setUpDataToHeadline: loading")
                    }

                    is HomeStates.Success -> {
                        Log.d(TAG, "setUpDataToHeadline: Success")
                        val handler = Handler(Looper.getMainLooper())
                        displayDataToHeadlines(it.news as List<Headlines>, binding)
                        var runnable: Runnable? = null
                        runnable = Runnable {
                            displayDataToHeadlines(it.news, binding)
                            runnable?.let { it1 -> handler.postDelayed(it1, 10000) }
                        }
                        handler.postDelayed(runnable, 10000)
                    }

                    is HomeStates.Error -> {
                        Log.d(TAG, "setUpDataToHeadline: Error $it ")
                    }

                }

            }

        }
    }

    private fun displayDataToHeadlines(news: List<Headlines>, binding: FragmentHomeBinding) {
        if (news.isNotEmpty()) {
            binding.relative.visibility = View.VISIBLE
            binding.learnMore.visibility = View.VISIBLE
            binding.imgIcon.visibility = View.VISIBLE
            val itemIndex = Random.nextInt(0, news.size)
            val item = news[itemIndex]

            Log.d(TAG, "displayDataToHeadlines: ${item.urlToImage}")

            Glide.with(requireContext())
                .load(item.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.bannerHome)

            binding.textTitleHome.text =
                if (item.title.isNotEmpty()) item.title else "Title Not Found"
            binding.learnMore.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                val url = Uri.parse(item.articleUrl)
                intent.data = url
                if (url != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUpRecyclerView(binding: FragmentHomeBinding) {
        lifecycleScope.launchWhenCreated {
            binding.listHome.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = adapter
            }

            viewModel.intentsChannel.send(HomeIntent.GetBreakingNews)

            viewModel.breakingNewsState.collect {
                when (it) {
                    is HomeStates.Idle -> {

                    }
                    is HomeStates.Loading -> {

                    }
                    is HomeStates.Success -> {
                        setUpRecyclerViewWithData(it.news as List<BreakingNews>, binding)
                    }
                    is HomeStates.Error -> {
                        Log.d(TAG, "setUpRecyclerView: $it")
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                        //Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun setUpRecyclerViewWithData(news: List<BreakingNews>, binding: FragmentHomeBinding) {
        binding.bar.visibility = View.INVISIBLE
        Log.d(TAG, "onViewCreated: ${news.size}")
        adapter = NewsAdapter(requireContext(), news)
        adapter.clickListener = this
        binding.listHome.adapter = adapter
    }

//    private fun isPhoneConnected(): Boolean {
//        val connectivityManager =
//            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
//            .isConnected
//    }


    override fun onItemClickListener(breakingNews: BreakingNews) {
        //Log.d(TAG, "onItemClickListener: ${breakingNews.articles[0].title}")
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToNewsProfileFragment(breakingNews)
        )
    }
}