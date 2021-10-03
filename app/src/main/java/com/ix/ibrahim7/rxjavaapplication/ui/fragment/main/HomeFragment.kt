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
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.ix.ibrahim7.rxjavaapplication.BR
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenericAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MenuAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentHomeBinding
import com.ix.ibrahim7.rxjavaapplication.model.menu.MenuItem
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.model.video.MovieVideo
import com.ix.ibrahim7.rxjavaapplication.other.EnumConstant
import com.ix.ibrahim7.rxjavaapplication.other.ONE
import com.ix.ibrahim7.rxjavaapplication.other.getApiLang
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.VideoPlayerDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.TYPE
import com.ix.ibrahim7.rxjavaapplication.util.OnScrollListener
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import com.ix.ibrahim7.rxjavaapplication.util.ZoomAnimation
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(),
    MenuAdapter.onClick,
    GenericAdapter.OnListItemViewClickListener<Content>{

    private val mBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val sliderAdapter by lazy {
        GenericAdapter(R.layout.item_image_slider,BR.Slider,
            object :GenericAdapter.OnListItemViewClickListener<Content>{
                override fun onClickItem(itemViewModel: Content, type: Int) {
                    when(type){
                        1->{
                            val bundle = Bundle().apply {
                                putInt(Constant.MOVIE_ID,itemViewModel.id!!.toInt())
                            }
                            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
                        }
                    }
                }
            }
        )
    }

    private val mainMovieAdapter by lazy {
        GenericAdapter(R.layout.item_main_movie,BR.Movie,this)
    }

    private val trailerAdapter by lazy {
        GenericAdapter(R.layout.item_trailer,BR.MovieTrailer,object : GenericAdapter.OnListItemViewClickListener<Content>{
            override fun onClickItem(itemViewModel: Content, type: Int) {
                viewModel.getMovieVideo(itemViewModel.id.toString(), requireContext().getApiLang())
                subscribeToMovieVideoObserver()
            }
        })
    }

    private val trendingAdapter by lazy {
            GenericAdapter(R.layout.item_full_movie_width,BR.FullMovieDetails, this)
    }

    private val menuAdapter by lazy {
        MenuAdapter(ArrayList(),this)
    }

    @Inject
    lateinit var viewModel: HomeViewModel
    private var loadingDialog : LoadingDialog ?= null
    private var selectedItemPos = 0
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
        setUpViewpager()

        menuAdapter.data.apply {
            clear()
            add(MenuItem(EnumConstant.POPULAR,getString(R.string.popular),true))
            add(MenuItem(EnumConstant.NEW,getString(R.string.now),false))
            add(MenuItem(EnumConstant.COMING_SOON,getString(R.string.soon),false))
        }

        with(mBinding){
           listMenu.apply {
                adapter = menuAdapter
            }



           btnMorePupuler.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt(TYPE,1)
                }
                findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
            }

           pupularList.apply {
                adapter = mainMovieAdapter
            }

            rcTrailer.apply {
                adapter = trailerAdapter
            }

            rcTrending.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = trendingAdapter
                addOnScrollListener(onScrollListener)
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    Glide.with(requireActivity())
                        .load(Constant.IMAGE_URL + sliderAdapter.differ.currentList[position].backdropPath)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(bitmapTransform(BlurTransformation(16, 3)))
                        .into(mBinding.tvImageSliderBackground)
                }
            })

        }

        subscribeToPopularObserver()
        subscribeToTrailerObserver()
        subscribeToTrendingObserver()


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
                        mainMovieAdapter.data = movie.contents!!
                        mainMovieAdapter.notifyDataSetChanged()
                        sliderAdapter.data = movie.contents!!
                        sliderAdapter.notifyDataSetChanged()
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
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        trailerAdapter.data = movie.contents!!
                        trailerAdapter.notifyDataSetChanged()
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
            viewModel.dataUpcomingLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        mainMovieAdapter.data = movie.contents!!
                        mainMovieAdapter.notifyDataSetChanged()
                    }
                    ResultRequest.Status.ERROR -> {
                    }
                }
            })
        }
    }

    private fun subscribeToNowPlayingObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.dataNowPlayingLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        mainMovieAdapter.data = movie.contents!!
                        mainMovieAdapter.notifyDataSetChanged()

                    }
                    ResultRequest.Status.ERROR -> {
                    }
                }
            })
        }
    }

    private fun subscribeToTrendingObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataTrendingLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        val movie = resultResponse.data!! as Movie
                        onScrollListener.totalCount = movie.totalPages!!
                        trendingAdapter.submitList(movie.contents!!)
                        mBinding.progressBar.visibility = View.GONE
                        if (isFirstLaunch){
                            isFirstLaunch = false
                            trendingAdapter.notifyDataSetChanged()
                        }
                    }
                    ResultRequest.Status.ERROR -> {
                        mBinding.progressBar.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun subscribeToMovieVideoObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataMovieVideoLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {

                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        (resultResponse.data as MovieVideo).let { video ->
                            if (!video.results.isNullOrEmpty())
                            VideoPlayerDialog(video.results!![0].toString()).show(childFragmentManager,"")
                        }

                    }
                    ResultRequest.Status.ERROR -> {
                        Log.e("eee error movie video",resultResponse.message.toString())
                    }
                }
            })
        }
    }

    private fun setUpViewpager() {
        mBinding.viewPager.apply {
            adapter = sliderAdapter
            setPageTransformer(ZoomAnimation())
        }
    }

    private val onScrollListener =
        OnScrollListener(isLoading, isLastPage, 0) {
            viewModel.getTrendingMovie(requireContext().getApiLang())
            isScrolling = false
        }

    override fun onClickListener(menuitem: MenuItem, position: Int) {
        menuAdapter.data[selectedItemPos].isSelected=false
        menuitem.isSelected=true
        selectedItemPos =position
        menuAdapter.notifyDataSetChanged()

        when(menuitem.code){
            EnumConstant.POPULAR ->{
                viewModel.getPopularMovie(requireContext().getApiLang())
                subscribeToPopularObserver()
            }
            EnumConstant.NEW ->{
                viewModel.getNowPlaying(requireContext().getApiLang())
                subscribeToNowPlayingObserver()
            }
            EnumConstant.COMING_SOON ->{
                viewModel.getUpComingMovie(requireContext().getApiLang())
                subscribeToUpComingObserver()
            }
        }

    }

    override fun onClickItem(itemViewModel: Content, type: Int) {
        when(type){
            ONE ->{
                val bundle = Bundle().apply {
                    putInt(Constant.MOVIE_ID,itemViewModel.id!!.toInt())
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
            }
        }
    }


}