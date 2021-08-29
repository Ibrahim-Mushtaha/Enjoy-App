package com.ix.ibrahim7.rxjavaapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.ImageSliderAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MenuAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentHomeBinding
import com.ix.ibrahim7.rxjavaapplication.model.MenuItem
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Content
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.TYPE
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import com.ix.ibrahim7.rxjavaapplication.util.ZoomAnimation
import jp.wasabeef.glide.transformations.BlurTransformation


class HomeFragment : Fragment(),MovieAdapter.onClick ,MenuAdapter.onClick {

    lateinit var mBinding:FragmentHomeBinding
    private var selectedItemPos = 0

    private val image_adapter by lazy {
        ImageSliderAdapter(ArrayList())
    }

    private val pupular_adapter by lazy {
        MovieAdapter(ArrayList(),1,this)
    }
    private val pupularAdapter by lazy {
        MovieAdapter(ArrayList(),3,this)
    }


    private val upcoming_adapter by lazy {
        MovieAdapter(ArrayList(),2,this)
    }

    private val menu_adapter by lazy {
        MenuAdapter(ArrayList(),this)
    }


    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    val array = ArrayList<Content>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewpager()
      //  requireActivity().setToolbarView(mBinding.toolbarView, "Home", true) {}

        menu_adapter.data.apply {
            clear()
            add(MenuItem(1,"POPULAR",true))
            add(MenuItem(1,"NOW",false))
            add(MenuItem(1,"SOON",false))
        }

        mBinding.listMenu.apply {
            adapter=menu_adapter
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


        mBinding.btnMoreUpcoming.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(TYPE,2)
            }
            findNavController().navigate(R.id.action_homeFragment_to_allListFragment,bundle)
        }

        mBinding.pupularList.apply {
            adapter = pupular_adapter
        }

        mBinding.upcomingList.apply {
            adapter = upcoming_adapter
        }

        viewModel.dataPupularLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    Log.e("eee data",it.data.toString())
                    pupular_adapter.data.clear()
                    pupular_adapter.data.addAll(it.data!!.contents!!)
                    pupular_adapter.notifyDataSetChanged()
                    pupularAdapter.data.clear()
                    pupularAdapter.data.addAll(it.data!!.contents!!)
                    pupularAdapter.notifyDataSetChanged()
                    Constant.dialog.dismiss()
                }
                is Resource.Error -> {
                    Log.e("eeee Error",it.message.toString())
                    Constant.dialog.dismiss()
                }
                is Resource.Loading -> {
                    Constant.showDialog(requireActivity())
                }
            }
        })


        viewModel.dataUpcomingLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    Log.e("eee data",it.data.toString())
                    upcoming_adapter.data.clear()
                    upcoming_adapter.data.addAll(it.data!!.contents!!)
                    upcoming_adapter.notifyDataSetChanged()
                 //   Constant.dialog.dismiss()
                }
                is Resource.Error -> {
                    Log.e("eeee Error",it.message.toString())
                  //  Constant.dialog.dismiss()
                }
                is Resource.Loading -> {
                  //  Constant.showDialog(requireActivity())
                }
            }
        })

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Glide.with(requireActivity())
                    .load(Constant.IMAGE_URL + pupularAdapter.data[position].backdropPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(bitmapTransform(BlurTransformation(16, 3)))
                    .into(mBinding.tvImageSliderBackground)
            }
        })
    }



    private fun setUpViewpager() {
        image_adapter.data.clear()
        image_adapter.data.add(R.drawable.ic_slider1)
        image_adapter.data.add(R.drawable.ic_slider2)
        view_pager.apply {
            if (image_adapter.data.size == 0) {
                view_pager.visibility = View.GONE
            }
            Glide.with(requireActivity())
                .load(R.drawable.ic_slider1)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(bitmapTransform(BlurTransformation(8, 1)))
                .into(mBinding.tvImageSliderBackground)
            adapter = pupularAdapter
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
        menu_adapter.data[selectedItemPos].isSelected=false
        menuitem.isSelected=true
        selectedItemPos =position
        menu_adapter.notifyDataSetChanged()

        when(menuitem.code){

        }

    }


}