package com.ix.ibrahim7.rxjavaapplication.ui.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ix.ibrahim7.rxjavaapplication.BR
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenericAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentTvshowBinding
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import com.ix.ibrahim7.rxjavaapplication.util.Resource

class TvShowFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Content>{

    private val mBinding by lazy {
        FragmentTvshowBinding.inflate(layoutInflater)
    }

    private val tvShowAdapter by lazy {
        GenericAdapter(R.layout.item_movie, BR.Movie,this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private var loadingDialog : LoadingDialog?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog =  LoadingDialog.getInstance()
        requireActivity().setToolbarView(mBinding.toolbarLayout,"Tv Show",true){}

        with(mBinding){

            rcTvShow.apply {
                adapter = tvShowAdapter
            }

            viewModel.dataUpcomingLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it) {
                    is Resource.Success -> {
                        Log.e("eee data",it.data.toString())
                        tvShowAdapter.submitList(it.data!!.contents!!)
                        try {
                            loadingDialog!!.dismiss()
                        }catch (e:Exception) {}
                    }
                    is Resource.Error -> {
                        Log.e("eeee Error",it.message.toString())
                        loadingDialog!!.dismiss()
                    }
                    is Resource.Loading -> {
                        loadingDialog!!.show(childFragmentManager,"")
                    }
                }
            })

        }

    }

    override fun onClickItem(itemViewModel: Content, type: Int) {

    }

}