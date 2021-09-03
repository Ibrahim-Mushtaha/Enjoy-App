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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.GenresAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.MovieAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.RecommendationsAdapter
import com.ix.ibrahim7.rxjavaapplication.adapter.ReviewsAdapter
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentDetailsBinding
import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.model.review.Reviews
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.DetailsViewModel
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import kotlinx.android.synthetic.main.fragment_details.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.IMAGE_URL
import com.ix.ibrahim7.rxjavaapplication.util.Constant.MOVIE_ID
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setImage
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(),MovieAdapter.onClick,RecommendationsAdapter.onClick,ReviewsAdapter.onClick {

    lateinit var mBinding: FragmentDetailsBinding

    @Inject
    lateinit var viewModel: DetailsViewModel

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



        subscribeToMovieDetailsObserver()
        subscribeToMovieReviewsObserver()
        subscribeToMovieRecommendationObserver()
        subscribeToMovieSimilarObserver()




    }

    private fun subscribeToMovieDetailsObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataDetailsLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {

                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        (resultResponse.data as MovieDetails).let { movie ->
                            mBinding.apply {
                                tvImage.startAnimation(
                                    AnimationUtils.loadAnimation(requireContext(),
                                        R.anim.slide_up
                                    ))
                                setImage(
                                    requireContext(),
                                    IMAGE_URL + movie.posterPath,
                                    tvImage,
                                    R.color.purple
                                )
                                genres_adapter.data.addAll(movie.genres!!)
                                genres_adapter.notifyDataSetChanged()
                                tvMovieName.text = movie.title
                                movieRating.rating = (movie.voteAverage!! / 2).toFloat()
                                tvRating.text = (movie.voteAverage / 2).toFloat().toString()
                                tvMovieDescription.text = movie.overview
                                tvReleaseDay.text = movie.releaseDate
                                tvViewerCount.text = movie.voteCount.toString()
                                Glide.with(requireActivity())
                                    .load(IMAGE_URL + movie.backdropPath)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .apply(RequestOptions.bitmapTransform(BlurTransformation(16, 3)))
                                    .into(mBinding.tvMovieBackgroundImage)

                                tvMovieDescription.startAnimation(
                                    AnimationUtils.loadAnimation(requireContext(),
                                        R.anim.slide_in_left
                                    ))
                            }
                        }

                    }
                    ResultRequest.Status.ERROR -> {

                    }
                }
            })
        }
    }

    private fun subscribeToMovieReviewsObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataReviewsLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {

                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        (resultResponse.data as Reviews).let { review ->
                            mBinding.apply {
                                    if (review.contents!!.isNotEmpty()) {
                                        reviews_adapter.data.clear()
                                        reviews_adapter.data.addAll(review.contents)
                                        reviews_adapter.notifyDataSetChanged()
                                    }
                                }
                        }

                    }
                    ResultRequest.Status.ERROR -> {

                    }
                }
            })
        }
    }

    private fun subscribeToMovieRecommendationObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataRecommendationLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {

                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        (resultResponse.data as Movie).let { movie ->
                            mBinding.apply {
                                    if (movie.contents!!.isNotEmpty()) {
                                        recommendations_adapter.data.clear()
                                        recommendations_adapter.data.addAll(movie.contents!!)
                                        recommendations_adapter.notifyDataSetChanged()
                                    }
                                }
                        }

                    }
                    ResultRequest.Status.ERROR -> {

                    }
                }
            })
        }
    }

  private fun subscribeToMovieSimilarObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataSimilarLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {

                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        (resultResponse.data as Movie).let { movie ->
                            mBinding.apply {
                                    if (movie.contents!!.isNotEmpty()) {
                                        movie_adapter.data.clear()
                                        movie_adapter.data.addAll(movie.contents)
                                        movie_adapter.notifyDataSetChanged()
                                    }
                                }
                        }

                    }
                    ResultRequest.Status.ERROR -> {

                    }
                }
            })
        }
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