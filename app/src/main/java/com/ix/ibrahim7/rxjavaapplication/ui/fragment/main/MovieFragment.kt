package com.ix.ibrahim7.rxjavaapplication.ui.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.ix.ibrahim7.rxjavaapplication.BR
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenericAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentMovieBinding
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.getApiLang
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.MovieViewModel
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.OnScrollListener
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
    lateinit var viewModel: MovieViewModel
    private var loadingDialog : LoadingDialog ?= null
    private var isFirstLaunch = true
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

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
                itemAnimator = DefaultItemAnimator()
                adapter = movieAdapter
                addOnScrollListener(onScrollListener)
            }

            subscribeToDiscoverObserver()

        }


    }


    private fun subscribeToDiscoverObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataDiscoverMovieLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when(resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        if (isFirstLaunch) {
                            loadingDialog!!.show(childFragmentManager, "")
                            isFirstLaunch = false
                        }else {
                            mBinding.progressBar.visibility = View.VISIBLE
                        }
                    }
                    ResultRequest.Status.SUCCESS -> {
                        val movie = resultResponse.data!! as Movie
                        onScrollListener.totalCount = movie.totalPages!!
                        movieAdapter.submitList(movie.contents!!)
                            try {
                                loadingDialog!!.dismiss()
                            }catch (e:Exception) {}
                            mBinding.progressBar.visibility = View.GONE
                        Log.e("eee dataUpcoming", movie.toString())
                    }
                    ResultRequest.Status.ERROR -> {
                        if (!isFirstLaunch) {
                            try {
                                loadingDialog!!.dismiss()
                            }catch (e:Exception) {}
                        }
                        else{
                            mBinding.progressBar.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }


    private val onScrollListener =
        OnScrollListener(isLoading, isLastPage, 0) {
            viewModel.getDiscoverMovie(requireContext().getApiLang())
            isScrolling = false
        }


    override fun onClickItem(itemViewModel: Content, type: Int) {
        when(type){
            1->{
                val bundle = Bundle().apply {
                    putInt(Constant.MOVIE_ID,itemViewModel.id!!.toInt())
                }
                findNavController().navigate(R.id.action_movieFragment2_to_detailsFragment,bundle)
            }
        }
    }

}