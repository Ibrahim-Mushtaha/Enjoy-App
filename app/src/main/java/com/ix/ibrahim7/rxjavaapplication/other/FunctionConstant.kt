package com.ix.ibrahim7.rxjavaapplication.other

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.rxjavaapplication.BuildConfig
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ToolbarLayoutBinding
import com.ix.ibrahim7.rxjavaapplication.ui.activity.MainActivity
import java.io.*
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

fun Activity.launchInstagram(username: String) {
    val uri = Uri.parse("http://instagram.com/_u/$username")
    val likeIng = Intent(Intent.ACTION_VIEW, uri)

    likeIng.setPackage("com.instagram.android")
    try {
        startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://instagram.com/xxx")
            )
        )
    }
}

fun Activity.shareApplication() {
    val app: ApplicationInfo = this.application.applicationInfo
    val filePath = app.sourceDir
    val intent = Intent(Intent.ACTION_SEND)

    intent.type = "*/*"

    val originalApk = File(filePath)
    try {
        //Make new directory in new location=
        var tempFile =
            File(this.externalCacheDir.toString() + "/ExtractedApk")
        //If directory doesn't exists create new
        if (!tempFile.isDirectory) if (!tempFile.mkdirs()) return
        //Get application's name and convert to lowercase
        tempFile = File(
            tempFile.path + "/" + getString(app.labelRes).replace(" ", "")
                .toLowerCase() + ".apk"
        )
        //If file doesn't exists create new
        if (!tempFile.exists()) {
            if (!tempFile.createNewFile()) {
                return
            }
        }
        //Copy file to new location
        val `in`: InputStream = FileInputStream(originalApk)
        val out: OutputStream = FileOutputStream(tempFile)
        val buf = ByteArray(1024)
        var len: Int
        while (`in`.read(buf).also { len = it } > 0) {
            out.write(buf, 0, len)
        }
        `in`.close()
        out.close()
        println("File copied.")
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
        val photoURI = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID.toString() + ".provider",
            tempFile
        )
        //  intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
        intent.putExtra(Intent.EXTRA_STREAM, photoURI)
        startActivity(Intent.createChooser(intent, "Share app via"))
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun getNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_up)
        .setExitAnim(R.anim.slide_down)
        .setPopEnterAnim(R.anim.slide_up)
        .setPopExitAnim(R.anim.slide_down)
        .build()
}



