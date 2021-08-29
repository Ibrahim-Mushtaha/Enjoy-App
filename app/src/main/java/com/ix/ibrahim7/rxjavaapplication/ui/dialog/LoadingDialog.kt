package com.ix.ibrahim7.rxjavaapplication.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ix.ibrahim7.rxjavaapplication.databinding.DialogLoadingBinding

class LoadingDialog : DialogFragment() {

    private lateinit var mBinding: DialogLoadingBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogLoadingBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        isCancelable = false
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


}