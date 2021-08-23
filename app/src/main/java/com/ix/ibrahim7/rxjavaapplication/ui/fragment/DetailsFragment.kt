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
import com.ix.ibrahim7.rxjavaapplication.adapter.GenresAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.RecommendationsAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.ReviewsAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentDetailsBinding
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Content
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.IMAGE_URL
import com.ix.ibrahim7.rxjavaapplication.util.Constant.MOVIE_ID
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setImage
import com.ix.ibrahim7.rxjavaapplication.util.Resource

class DetailsFragment : Fragment(),MovieAdapter.onClick,RecommendationsAdapter.onClick,ReviewsAdapter.onClick {

    lateinit var mBinding: FragmentDetailsBinding


    private val viewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    private val genres_adapter by lazy {
        GenresAdapter(ArrayList())
    }

    private val movie_adapter by lazy {
        MovieAdapter(ArrayList(),1,this)
    }

    private val reviews_adapter by lazy {
        ReviewsAdapter(ArrayList(),this)
    }

    private val recommendations_adapter by lazy {
        RecommendationsAdapter(ArrayList(),this)
    }

    private val getMovieID by lazy {
        requireArguments().getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        viewModel.getMovieDetails(getMovieID)
        viewModel.getMovieReviews(getMovieID)
        viewModel.getMovieRecommendations(getMovieID)
        viewModel.getSimillerMovie(getMovieID)

        mBinding.apply {
            genresList.apply {
                adapter=genres_adapter
            }

            similarList.apply {
                adapter=movie_adapter
            }
            recommendations_list.apply {
                adapter=recommendations_adapter
            }
            reviewslist.apply {
                adapter=reviews_adapter
            }
        }



        viewModel.dataDetailsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movie ->
                        mBinding.apply {
                            tvImage.startAnimation(
                                AnimationUtils.loadAnimation(requireContext(),
                                    R.anim.slide_up
                                ))
                            setImage(
                                requireContext(),
                                IMAGE_URL + movie.posterPath,
                                tvImage,
                                R.color.background_color
                            )
                            genres_adapter.data.addAll(movie.genres!!)
                            genres_adapter.notifyDataSetChanged()
                            tvMovieName.text = movie.title
                            movieRating.rating = (movie.voteAverage!! / 2).toFloat()
                            tvMovieDescription.text = movie.overview
                            tvMovieDescription.startAnimation(
                                AnimationUtils.loadAnimation(requireContext(),
                                    R.anim.slide_in_left
                                ))
                        }
                    }
                }
                is Resource.Error -> {
                    Log.e("eeee Error", it.message.toString())
                }
                is Resource.Loading -> {
                }
            }
        })



        viewModel.dataReviewsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { review ->
                        if (review.contents!!.isNotEmpty()) {
                            reviews_adapter.data.clear()
                            reviews_adapter.data.addAll(review.contents)
                            reviews_adapter.notifyDataSetChanged()
                        }
                    }
                }
                is Resource.Error -> {
                    Log.e("eeee Error", it.message.toString())
                }
                is Resource.Loading -> {
                }
            }
        })

        viewModel.dataRecommendetionLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movie ->
                            recommendations_adapter.data.clear()
                            recommendations_adapter.data.addAll(movie.contents!!)
                            recommendations_adapter.notifyDataSetChanged()
                          Log.e("eee Recommendetion",it.data.toString())
                    }
                }
                is Resource.Error -> {
                    Log.e("eeee Error", it.message.toString())
                }
                is Resource.Loading -> {
                }
            }
        })

        viewModel.dataSimilerLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movie ->
                            movie_adapter.data.clear()
                            movie_adapter.data.addAll(movie.contents!!)
                            movie_adapter.notifyDataSetChanged()
                          Log.e("eee Similer",it.data.toString())
                    }
                }
                is Resource.Error -> {
                    Log.e("eeee Error", it.message.toString())
                }
                is Resource.Loading -> {
                }
            }
        })


    }


    override fun onClickItem(content: Content, position: Int, type: Int) {
        when(type){
            1->{
                when(type){
                    1->{
                        val bundle = Bundle().apply {
                            putInt(MOVIE_ID,content.id!!.toInt())
                        }
                        findNavController().navigate(R.id.action_detailsFragment_self,bundle)
                    }
                }
            }
        }
    }



    override fun onClickItem(
        content: com.ix.ibrahim7.rxjavaapplication.model.review.Content,
        position: Int,
        type: Int
    ) {

    }


}