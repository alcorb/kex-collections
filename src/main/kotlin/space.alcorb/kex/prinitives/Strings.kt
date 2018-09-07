package space.alcorb.kex.prinitives

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
@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
         Html.fromHtml(this)
    }
}

fun String.asHexToColor(): Int {
    val rawColor = if (this[0] == '#') this else "#$this"
    return Color.parseColor(rawColor)
}

fun String.matches(pattern: String) = this.matches(Regex(pattern))