package com.ix.ibrahim7.rxjavaapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentAllListBinding
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.MOVIE_ID
import com.ix.ibrahim7.rxjavaapplication.util.Constant.TYPE
import com.ix.ibrahim7.rxjavaapplication.util.OnScrollListener
import com.ix.ibrahim7.rxjavaapplication.util.Resource



class AllListFragment : Fragment(),MovieAdapter.onClick {

    private val mBinding by lazy {
        FragmentAllListBinding.inflate(layoutInflater)
    }

    private val listAdapter by lazy {
        MovieAdapter(ArrayList(),4,this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

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
              viewModel.dataPupularLiveData.observe(
                  viewLifecycleOwner,
                  androidx.lifecycle.Observer {
                      when (it) {
                          is Resource.Success -> {
                              Log.e("eee data", it.data.toString())
                              listAdapter.data.clear()
                              listAdapter.data.addAll(it.data!!.contents!!)
                              listAdapter.notifyDataSetChanged()
                              try {
                                  loadingDialog!!.dismiss()
                              }catch (e:Exception) {}
                          }
                          is Resource.Error -> {
                              Log.e("eeee Error", it.message.toString())
                              try {
                                  loadingDialog!!.dismiss()
                              }catch (e:Exception) {}
                          }
                          is Resource.Loading -> {
                              loadingDialog!!.show(childFragmentManager,"")
                          }
                      }
                  })
          }
          2 -> {
              viewModel.dataUpcomingLiveData.observe(
                  viewLifecycleOwner,
                  androidx.lifecycle.Observer {
                      when (it) {
                          is Resource.Success -> {
                              isLoading = false
                              onScrollListener.totalCount = it.data!!.totalResults!!
                              listAdapter.data.clear()
                              listAdapter.data.addAll(it.data.contents!!)
                              listAdapter.notifyDataSetChanged()
                              Log.e("eee dataUpcoming", it.data.toString())
                              Constant.dialog.dismiss()
                          }
                          is Resource.Error -> {
                              isLoading = false
                              Log.e("eeee Error", it.message.toString())
                              Constant.dialog.dismiss()
                          }
                          is Resource.Loading -> {
                              isLoading = true
                              Constant.showDialog(requireActivity())
                          }
                      }
                  })
          }
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