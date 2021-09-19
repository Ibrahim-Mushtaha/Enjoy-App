package com.ix.ibrahim7.rxjavaapplication.ui.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ix.ibrahim7.rxjavaapplication.BR
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenericAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentMovieBinding
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Content>{

    private val mBinding by lazy {
        FragmentMovieBinding.inflate(layoutInflater)
    }

    private val movieAdapter by lazy {
        GenericAdapter(R.layout.item_movie,BR.Movie,this)
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    private var loadingDialog : LoadingDialog ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog =  LoadingDialog.getInstance()

        requireActivity().setToolbarView(mBinding.toolbarLayout,getString(R.string.movie),true){}

        with(mBinding){

            rcMovie.apply {
                adapter = movieAdapter
            }

            subscribeToPopularObserver()

        }


    }


    private fun subscribeToPopularObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataPopularLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when(resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        loadingDialog!!.show(childFragmentManager,"")
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        movieAdapter.submitList(movie.contents!!)
                        try {
                            loadingDialog!!.dismiss()
                        }catch (e:Exception) {}
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



    override fun onClickItem(itemViewModel: Content, type: Int) {

    }

}