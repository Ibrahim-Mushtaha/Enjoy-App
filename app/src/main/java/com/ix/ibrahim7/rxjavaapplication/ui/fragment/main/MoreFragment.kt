package com.ix.ibrahim7.rxjavaapplication.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.FragmentMoreBinding
import com.ix.ibrahim7.rxjavaapplication.other.*
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LanguageDialog
import com.ix.ibrahim7.rxjavaapplication.ui.dialog.NotificationStatusDialog
import com.ix.ibrahim7.rxjavaapplication.util.PreferencesManager

class MoreFragment : Fragment(),
    LanguageDialog.OnSaveLanguage{

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

        requireActivity().setToolbarView(mBinding.toolbarLayout,getString(R.string.more),false){
            findNavController().navigateUp()
        }

        with(mBinding){

            when {
                PreferencesManager(requireContext()).sharedPreference.getString(LANG,"ar").toString() == "en" -> {
                    txtLanguage.text = getString(R.string.english)
                }
                else -> {
                    txtLanguage.text = getString(R.string.arabic)
                }
            }


            btnNotification.setOnClickListener {
                NotificationStatusDialog().show(childFragmentManager,"")
            }

            btnLanguage.setOnClickListener {
                LanguageDialog(this@MoreFragment).show(childFragmentManager,"")
            }

            btnShare.setOnClickListener {
                requireActivity().shareApplication()
            }

            btnLaunchInstagram.setOnClickListener {
                requireActivity().launchInstagram(INSTAGRAM_USERNAME)
            }

        }

    }

    override fun onSaveLanguage() {
        requireActivity().restartActivity()
    }

}