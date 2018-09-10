package space.alcorb.kex.android.actions

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import space.alcorb.kex.Kex
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Common Android actions.
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
fun Context.launchAppOrToast(intent: Intent, error: String) {
    if (canLaunchIntent(intent))
        this.startActivity(intent)
    else
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
}

fun canLaunchIntent(intent: Intent): Boolean {
    val pm = Kex.instance.context.packageManager
    val res = pm.queryIntentActivities(intent, 0)
    return (res != null && res.size != 0)
}

fun Context.shareTextToOtherApp(shareBody: String?, sendWithTitle: String, appsNotFoundText: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, shareBody ?: "")
    
    launchAppOrToast(Intent.createChooser(intent, sendWithTitle), appsNotFoundText)
}

fun Context.openAppByPackage(pack: String, appNotFoundText: String) {
    val launchIntent = packageManager.getLaunchIntentForPackage(pack)
    launchAppOrToast(launchIntent, appNotFoundText)
}

fun Context.openAppInGooglePlayOrInBrowser(pack: String) {
    val inMarketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pack"))

    if (canLaunchIntent(inMarketIntent)) {
        startActivity(inMarketIntent)
    } else {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("http://play.google.com/store/apps/details?id=$pack")
        startActivity(i)
    }
}

fun Context.emailTo(email: String?, mailAgentsNotFoundText: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", email, null
    ))
    launchAppOrToast(Intent.createChooser(emailIntent, null), mailAgentsNotFoundText)
}

fun Context.callTo(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts(
        "tel", phone, null
    ))
    startActivity(intent)
}

fun Context.openUrl(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/**
 * @param authorityString - "ru.trinitydigital.***.storage.provider"
 */
fun AppCompatActivity.dispatchToTakePhoto(REQUEST_TAKE_PHOTO: Int, authorityString: String) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (canLaunchIntent(intent)) {
        var photoFile: File? = null
        try {
            photoFile = createImageFile(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        photoFile?.let {
            val photoURI = FileProvider.getUriForFile(this, authorityString, it)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                else -> {
                    val clip = ClipData.newUri(contentResolver, "A photo", photoURI)
                    intent.clipData = clip
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
            }
            
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        }
    }
}

/**
 * @param authorityString - "ru.trinitydigital.***.storage.provider"
 */
fun Fragment.dispatchToTakePhoto(REQUEST_TAKE_PHOTO: Int, authorityString: String) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (canLaunchIntent(intent)) {
        var photoFile: File? = null
        try {
            photoFile = createImageFile(context!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        photoFile?.let {
            val photoURI = FileProvider.getUriForFile(context!!, authorityString, it)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                else -> {
                    val clip = ClipData.newUri(context!!.contentResolver, "A photo", photoURI)
                    intent.clipData = clip
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
            }
            
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        }
    }
}

private fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(imageFileName, ".jpg", storageDir)
}