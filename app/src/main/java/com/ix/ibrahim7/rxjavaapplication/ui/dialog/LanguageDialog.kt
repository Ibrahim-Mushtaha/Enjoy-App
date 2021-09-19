package com.ix.ibrahim7.rxjavaapplication.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.BottomSheetLanguageBinding
import com.ix.ibrahim7.rxjavaapplication.other.*
import com.ix.ibrahim7.rxjavaapplication.util.PreferencesManager

class LanguageDialog(val onSaveLanguage: OnSaveLanguage) : BottomSheetDialogFragment(){

    private val mBinding by lazy {
        BottomSheetLanguageBinding.inflate(layoutInflater)
    }

    private var lang = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)


        with(mBinding){


            if (PreferencesManager(requireContext()).sharedPreference.getString(LANG,"ar").toString() == "en"){
                lang="en"
                btnEnglish.isChecked = true
               btnArabic.isChecked = false
            }else{
                lang="ar"
                btnEnglish.isChecked = false
                btnArabic.isChecked = true
            }


            btnEnglish.setOnClickListener {
                PreferencesManager(requireContext()).editor.putString(LANG,"en").apply()
            }

            btnArabic.setOnClickListener {
                PreferencesManager(requireContext()).editor.putString(LANG,"ar").apply()
            }

            btnConfirm.setOnClickListener {
                if (PreferencesManager(requireContext()).sharedPreference.getString(LANG,"ar").toString() != lang){
                    onSaveLanguage.onSaveLanguage()
                }else{
                    dismiss()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    interface OnSaveLanguage {
        fun onSaveLanguage()
    }


}