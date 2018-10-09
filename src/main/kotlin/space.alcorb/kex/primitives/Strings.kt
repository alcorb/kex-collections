package space.alcorb.kex.primitives

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * Extensions for strings.
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */

fun String.fromHtml(): Spanned =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(this)
        }

fun String.asHexToColor(default: Int = Color.TRANSPARENT): Int =
        try {
            val rawColor = if (this[0] == '#') this else "#$this"
            Color.parseColor(rawColor)
        } catch (e: Exception) {
            default
        }

fun String.matches(pattern: String) = this.matches(Regex(pattern))