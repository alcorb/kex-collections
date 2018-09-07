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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Common Android actions.
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
private fun launchOrToast(
    context: Context,
    intent: Intent,
    errorStringId: Int
) {
    if (canLaunchIntent(context, intent))
        context.startActivity(intent)
    else
        Toast.makeText(context, errorStringId, Toast.LENGTH_SHORT).show()
}

fun canLaunchIntent(context: Context, intent: Intent): Boolean {
    val pm = context.packageManager
    val res = pm.queryIntentActivities(intent, 0)
    return (res != null && res.size != 0)
}

fun shareTextToOtherApp(
    context: Context,
    shareBody: String?,
    sendWithTitleId: Int,
    errorNoMessengerId: Int
) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, shareBody ?: "")

    launchOrToast(context, Intent.createChooser(intent, context.getString(sendWithTitleId)), errorNoMessengerId)
}

fun Context.openAppByPackage(pack: String) {
    val launchIntent = this.packageManager.getLaunchIntentForPackage(pack)
    this.startActivity(launchIntent)
}

fun Context.openAppInGooglePlayOrInBrowser(pack: String) {
    val inMarketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pack"))

    if (canLaunchIntent(this, inMarketIntent)) {
        this.startActivity(inMarketIntent)
    } else {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("http://play.google.com/store/apps/details?id=$pack")
        this.startActivity(i)
    }
}

fun emailTo(context: Context, email: String?) {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", email, null
    ))
    context.startActivity(Intent.createChooser(emailIntent, null))
}

fun callTo(context: Context, phone: String?) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts(
        "tel", phone, null
    ))
    context.startActivity(intent)
}

fun openUrl(context: Context, url: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/**
 * @param authorityString - "ru.trinitydigital.***.storage.provider"
 */
fun AppCompatActivity.dispatchToTakePhoto(REQUEST_TAKE_PHOTO: Int, authorityString: String) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (canLaunchIntent(this, intent)) {
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
    if (canLaunchIntent(context!!, intent)) {
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