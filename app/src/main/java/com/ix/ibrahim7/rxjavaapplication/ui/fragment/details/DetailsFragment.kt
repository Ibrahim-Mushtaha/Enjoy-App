package com.ix.ibrahim7.rxjavaapplication.ui.fragment.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ix.ibrahim7.rxjavaapplication.BR
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.adapter.*
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentDetailsBinding
import com.ix.ibrahim7.rxjavaapplication.model.details.Genre
import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.model.review.Reviews
import com.ix.ibrahim7.rxjavaapplication.model.video.MovieVideo
import com.ix.ibrahim7.rxjavaapplication.other.getApiLang
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.VideoPlayerDialog
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.IMAGE_URL
import com.ix.ibrahim7.rxjavaapplication.util.Constant.MOVIE_ID
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setImage
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(),
    GenericAdapter.OnListItemViewClickListener<Genre>{

    private val mBinding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: DetailsViewModel

    private val genresAdapter by lazy {
      GenericAdapter(R.layout.item_category,BR.Genre,this)
    }

    private val reviewsAdapter by lazy {
        GenericAdapter(R.layout.item_review,BR.Review,object : GenericAdapter.OnListItemViewClickListener<com.ix.ibrahim7.rxjavaapplication.model.review.Content>{
            override fun onClickItem(
                itemViewModel: com.ix.ibrahim7.rxjavaapplication.model.review.Content,
                type: Int
            ) {

            }

        })
    }

    private val similarMovieAdapter by lazy {
        GenericAdapter(R.layout.item_main_movie,BR.Movie,object :  GenericAdapter.OnListItemViewClickListener<Content>{
            override fun onClickItem(itemViewModel: Content, type: Int) {
                when(type){
                    1->{
                        when(type){
                            1->{
                                val bundle = Bundle().apply {
                                    putInt(MOVIE_ID,itemViewModel.id!!.toInt())
                                }
                                findNavController().navigate(R.id.action_detailsFragment_self,bundle)
                            }
                        }
                    }
                }
            }

        })
    }

    private val recommendationsAdapter by lazy {
        GenericAdapter(R.layout.item_main_movie,BR.Movie,
            object :GenericAdapter.OnListItemViewClickListener<Content>{
                override fun onClickItem(itemViewModel: Content, type: Int) {
                    when(type){
                        1->{
                            val bundle = Bundle().apply {
                                putInt(MOVIE_ID,itemViewModel.id!!.toInt())
                            }
                            findNavController().navigate(R.id.action_detailsFragment_self,bundle)
                        }
                    }
                }
            }
        )
    }

    private val getMovieID by lazy {
        requireArguments().getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(mBinding) {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            with(viewModel) {
                getMovieDetails(getMovieID, requireContext().getApiLang())
                getMovieReviews(getMovieID, requireContext().getApiLang())
                getMovieRecommendations(getMovieID, requireContext().getApiLang())
                getSimilarMovie(getMovieID, requireContext().getApiLang())
            }

                genresList.apply {
                    adapter = genresAdapter
                }

                similarList.apply {
                    adapter = similarMovieAdapter
                }
                recommendations_list.apply {
                    adapter = recommendationsAdapter
                }
                reviewslist.apply {
                    adapter = reviewsAdapter
                }

            btnTrailer.setOnClickListener {
                viewModel.getMovieVideo(getMovieID.toString(), requireContext().getApiLang())
                subscribeToMovieVideoObserver()
            }



            subscribeToMovieDetailsObserver()
            subscribeToMovieReviewsObserver()
            subscribeToMovieRecommendationObserver()
            subscribeToMovieSimilarObserver()
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

    private fun subscribeToMovieDetailsObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataDetailsLiveData.observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {

                    }
                    ResultRequest.Status.SUCCESS -> {
                        Log.e("eee data",resultResponse.data.toString())
                        (resultResponse.data as MovieDetails).let { movie ->
                            with(mBinding){
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
                                genresAdapter.data = movie.genres!!
                                genresAdapter.notifyDataSetChanged()
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
                                        reviewsAdapter.data = review.contents
                                        reviewsAdapter.notifyDataSetChanged()
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
                                        recommendationsAdapter.data = movie.contents!!
                                        recommendationsAdapter.notifyDataSetChanged()
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
                                        similarMovieAdapter.data = movie.contents!!
                                        similarMovieAdapter.notifyDataSetChanged()
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

    override fun onClickItem(itemViewModel: Genre, type: Int) {

    }


}