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
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.ix.ibrahim7.rxjavaapplication.BR
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenericAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.ImageSliderAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MenuAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentHomeBinding
import com.ix.ibrahim7.rxjavaapplication.model.MenuItem
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.EnumConstant
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.TYPE
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import com.ix.ibrahim7.rxjavaapplication.util.ZoomAnimation
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(),
    MovieAdapter.onClick ,
    MenuAdapter.onClick,
    GenericAdapter.OnListItemViewClickListener<Content>{

    private val mBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private var selectedItemPos = 0

    private val imageAdapter by lazy {
        ImageSliderAdapter(ArrayList())
    }

    private val pupular_adapter by lazy {
        MovieAdapter(ArrayList(),1,this)
    }

    private val trailerAdapter by lazy {
        GenericAdapter(R.layout.item_trailer,BR.MovieTrailer,this)
    }

    private val popularAdapter by lazy {
        MovieAdapter(ArrayList(),3,this)
    }


    private val upcomingAdapter by lazy {
        MovieAdapter(ArrayList(),2,this)
    }

    private val menuAdapter by lazy {
        MenuAdapter(ArrayList(),this)
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    val array = ArrayList<Content>()
    private var loadingDialog : LoadingDialog ?= null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog =  LoadingDialog.getInstance()
        setUpViewpager()

        menuAdapter.data.apply {
            clear()
            add(MenuItem(EnumConstant.POPULAR,getString(R.string.popular),true))
            add(MenuItem(EnumConstant.NEW,getString(R.string.now),false))
            add(MenuItem(EnumConstant.COMING_SOON,getString(R.string.soon),false))
        }

        mBinding.listMenu.apply {
            adapter = menuAdapter
        }


        /*mBinding.btnClick.setOnClickListener {
            viewModel.getPupular()
        }

        mBinding.btnClick2.setOnClickListener {
            Log.e("eee allData",array.toString())
        }*/

        mBinding.btnMorePupuler.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,1)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }


       /* mBinding.btnMoreUpcoming.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,2)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }*/

        mBinding.pupularList.apply {
            adapter = pupular_adapter
        }

        mBinding.rcTrailer.apply {
            adapter = trailerAdapter
        }

        mBinding.upcomingList.apply {
            adapter = upcomingAdapter
        }

        subscribeToPopularObserver()
     //   subscribeToUpComingObserver()
        subscribeToTrailerObserver()
        subscribeToTrendingObserver()

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Glide.with(requireActivity())
                    .load(Constant.IMAGE_URL + popularAdapter.data[position].backdropPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(bitmapTransform(BlurTransformation(16, 3)))
                    .into(mBinding.tvImageSliderBackground)
            }
        })
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
                        pupular_adapter.data.clear()
                        pupular_adapter.data.addAll(movie.contents!!)
                        pupular_adapter.notifyDataSetChanged()
                        popularAdapter.data.clear()
                        popularAdapter.data.addAll(movie.contents!!)
                        popularAdapter.notifyDataSetChanged()
                      //  trailerAdapter.submitList(movie.contents!!)
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

    private fun subscribeToTrailerObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataTopRatedLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        //loadingDialog!!.show(childFragmentManager,"")
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        trailerAdapter.submitList(movie.contents!!)
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

    /*private fun subscribeToUpComingObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataUpcomingLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        upcomingAdapter.data.clear()
                        upcomingAdapter.data.addAll(movie.contents!!)
                        upcomingAdapter.notifyDataSetChanged()

                    }
                    ResultRequest.Status.ERROR -> {
                    }
                }
            })
        }
    }*/

    private fun subscribeToTrendingObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataTrendingLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        upcomingAdapter.data.clear()
                        upcomingAdapter.data.addAll(movie.contents!!)
                        upcomingAdapter.notifyDataSetChanged()

                    }
                    ResultRequest.Status.ERROR -> {
                    }
                }
            })
        }
    }


    private fun setUpViewpager() {
        imageAdapter.data.clear()
       imageAdapter.data.add(R.drawable.ic_image1)
        imageAdapter.data.add(R.drawable.ic_image1)
        view_pager.apply {
            if (imageAdapter.data.size == 0) {
                view_pager.visibility = View.GONE
            }
            adapter = popularAdapter
            setPageTransformer(ZoomAnimation())
        }
    }

    override fun onClickItem(content: Content, position: Int, type: Int) {
        when(type){
            1->{
                val bundle = Bundle().apply {
                    putInt(Constant.MOVIE_ID,content.id!!.toInt())
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
            }
        }
    }

    override fun onClickListener(menuitem: MenuItem, position: Int) {
        menuAdapter.data[selectedItemPos].isSelected=false
        menuitem.isSelected=true
        selectedItemPos =position
        menuAdapter.notifyDataSetChanged()

        when(menuitem.code){
            EnumConstant.POPULAR ->{

            }
            EnumConstant.NEW ->{

            }
            EnumConstant.COMING_SOON ->{

            }
        }

    }

    override fun onClickItem(itemViewModel: Content, type: Int) {

    }


}