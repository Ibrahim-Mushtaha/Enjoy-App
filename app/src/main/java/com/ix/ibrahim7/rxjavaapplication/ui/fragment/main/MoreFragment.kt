package com.ix.ibrahim7.rxjavaapplication.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentMoreBinding
import com.ix.ibrahim7.rxjavaapplication.other.INSTAGRAM_USERNAME
import com.ix.ibrahim7.rxjavaapplication.other.launchInstagram
import com.ix.ibrahim7.rxjavaapplication.other.setToolbarView
import com.ix.ibrahim7.rxjavaapplication.other.shareApplication
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LanguageDialog
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.NotificationStatusDialog

class MoreFragment : Fragment(){

    private val mBinding by lazy {
        FragmentMoreBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarLayout,"More",false){
            findNavController().navigateUp()
        }

        with(mBinding){

            btnNotification.setOnClickListener {
                NotificationStatusDialog().show(childFragmentManager,"")
            }

            btnLanguage.setOnClickListener {
                LanguageDialog().show(childFragmentManager,"")
            }

            btnShare.setOnClickListener {
                requireActivity().shareApplication()
            }

            btnLaunchInstagram.setOnClickListener {
                requireActivity().launchInstagram(INSTAGRAM_USERNAME)
            }

        }

    }

}