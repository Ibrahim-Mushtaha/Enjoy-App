package com.ix.ibrahim7.rxjavaapplication.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentSplashBinding
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.SplashState
import com.ix.ibrahim7.rxjavaapplication.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var mBinding: FragmentSplashBinding

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSplashBinding.inflate(inflater,container,false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveData.observe(requireActivity(), Observer {
            when (it) {
                is SplashState.MainActivity -> {
                        GlobalScope.launch {
                            withContext(Dispatchers.Main){
                                delay(1400)
                                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                            }
                        }
                }
            }
        })


        super.onViewCreated(view, savedInstanceState)
    }

}