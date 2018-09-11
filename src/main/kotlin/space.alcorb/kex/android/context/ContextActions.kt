package space.alcorb.kex.android.context

import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Extensions for context holders.
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
inline fun <reified T> AppCompatActivity.extra(key: String, default: T?): Lazy<T?> = lazy {
    return@lazy (intent?.extras?.get(key) as? T) ?: default
}

inline fun <reified T> Fragment.extra(key: String, default: T? = null): Lazy<T?> = lazy {
    return@lazy (arguments?.get(key) as? T) ?: default
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.color(resId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getColor(resId)
        } else {
            ContextCompat.getColor(this, resId)
        }

fun Context.drawable(resId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getDrawable(resId)
        } else {
            ContextCompat.getDrawable(this, resId)
        }

fun Context.show(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun FragmentActivity.setStatusBarColor(colorId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        this.window.statusBarColor = ContextCompat.getColor(this, colorId)
}