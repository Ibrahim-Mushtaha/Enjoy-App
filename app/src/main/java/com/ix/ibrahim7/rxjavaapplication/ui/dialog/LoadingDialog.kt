package com.ix.ibrahim7.rxjavaapplication.ui.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        instance = null
        Log.e("eee dismiss","on dismess")
    }


    companion object{
        private var instance : LoadingDialog? = null
        fun getInstance() :LoadingDialog{
            if (instance == null){
                instance = LoadingDialog()
            }
            return instance!!
        }
    }


}