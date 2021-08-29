package com.ix.ibrahim7.rxjavaapplication.other

import android.app.*
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ToolbarLayoutBinding
import com.ix.ibrahim7.rxjavaapplication.ui.activity.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.*

fun Activity.setToolbarView(
    view: ToolbarLayoutBinding,
    title: String,
    main: Boolean,
    onComplete: (type:Int) -> Unit
): ToolbarLayoutBinding {

    view.tvTitle.text = title

    when (main) {
        true -> {
            view.btnBack.isVisible = false
        }
        else->{
            view.btnBack.apply {
                isVisible = true
                setOnClickListener {
                    onComplete(ONE)
                }
            }
        }
    }
    return view
}

fun Activity.getSnackBar(@LayoutRes layoutId: Int, view: View, message: String): View {
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)

    val custom = this.layoutInflater.inflate(layoutId, null)

    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackbarLayout.setPadding(0, 0, 0, 180)

    custom.findViewById<com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar>(R.id.item_progress).progress =
        100f

    custom.findViewById<TextView>(R.id.txtMessage).text = message

    snackbarLayout.addView(custom)
    snackbar.show()
    return custom
}


fun Activity.setLanguage(lan: String) {
    val res = resources
    val dr = res.displayMetrics
    val cr = res.configuration
    cr.setLocale(Locale(lan))
    res.updateConfiguration(cr, dr)
}

fun getDefaultLang(onComplete: (String) -> Unit){
    val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Resources.getSystem().configuration.locales.get(0)
    } else {
        Resources.getSystem().configuration.locale
    }
    val lang  = locale.displayLanguage
    if (lang == "English"){
        onComplete("en")
    }else{
        onComplete("ar")
    }
}

fun Activity.getPermission(
    permissions: ArrayList<String>,
    onSuccess: () -> Unit,
    onDenied: () -> Unit
) {
    Dexter.withContext(this)
        .withPermissions(
            permissions
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    when {
                        report.areAllPermissionsGranted() -> {
                            onSuccess()
                        }
                        else -> {
                            onDenied()
                        }
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }

        })
        .withErrorListener {}
        .check()
}

fun Activity.restartActivity(){
    finish()
    startActivity(
        Intent(
            this,
            MainActivity::class.java
        )
    )
    overridePendingTransition(
        android.R.anim.fade_in,
        android.R.anim.fade_out
    )
}



