package com.ix.ibrahim7.rxjavaapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentAllListBinding
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.MOVIE_ID
import com.ix.ibrahim7.rxjavaapplication.util.Constant.TYPE
import com.ix.ibrahim7.rxjavaapplication.util.OnScrollListener
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllListFragment : Fragment(),MovieAdapter.onClick {

    private val mBinding by lazy {
        FragmentAllListBinding.inflate(layoutInflater)
    }

    private val listAdapter by lazy {
        MovieAdapter(ArrayList(),4,this)
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    private val getType by lazy {
        requireArguments().getInt(TYPE)
    }

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false
    private var loadingDialog : LoadingDialog ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog =  LoadingDialog.getInstance()

        requireActivity().setToolbarView(mBinding.toolbarLayout,"All Movie",false){
            findNavController().navigateUp()
        }

        mBinding.listItem.apply {
            adapter=listAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.recyclerview_layout_animation)
            addOnScrollListener(onScrollListener)
        }

      when(getType) {
          1 -> {
             subscribeToPopularObserver()
          }
          2 -> {
              subscribeToUpComingObserver()
          }
      }



    }

    private fun subscribeToPopularObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataPopularLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        loadingDialog!!.show(childFragmentManager,"")
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        isLoading = false
                        onScrollListener.totalCount = movie.totalResults!!
                        listAdapter.data.clear()
                        listAdapter.data.addAll(movie.contents!!)
                        listAdapter.notifyDataSetChanged()
                        Log.e("eee dataUpcoming", movie.toString())
                        try {
                            loadingDialog!!.dismiss()
                        }catch (e:Exception) {}
                    }
                    ResultRequest.Status.ERROR -> {
                        Log.e("eeee Error",resultResponse.data.toString())
                        try {
                            loadingDialog!!.dismiss()
                        }catch (e:Exception) {}
                    }
                }
            })
        }
    }
    private fun subscribeToUpComingObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataPopularLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        loadingDialog!!.show(childFragmentManager,"")
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        isLoading = false
                        onScrollListener.totalCount = movie.totalResults!!
                        listAdapter.data.clear()
                        listAdapter.data.addAll(movie.contents!!)
                        listAdapter.notifyDataSetChanged()
                        Log.e("eee dataUpcoming", movie.toString())
                        try {
                            loadingDialog!!.dismiss()
                        }catch (e:Exception) {}
                    }
                    ResultRequest.Status.ERROR -> {
                        Log.e("eeee Error",resultResponse.data.toString())
                        try {
                            loadingDialog!!.dismiss()
                        }catch (e:Exception) {}
                    }
                }
            })
        }
    }

    private val onScrollListener = OnScrollListener(isLoading, isLastPage, 0) {
        //viewModel.getUpcoming()
        isScrolling = false
    }

    override fun onClickItem(content: Content, position: Int, type: Int) {
        when(type){
            1->{
                val bundle = Bundle().apply {
                    putInt(MOVIE_ID,content.id!!.toInt())
                }
                findNavController().navigate(R.id.action_allListFragment_to_detailsFragment,bundle)
            }
        }
    }



}