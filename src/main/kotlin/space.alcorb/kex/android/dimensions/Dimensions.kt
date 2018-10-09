package space.alcorb.kex.android.dimensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import space.alcorb.kex.Kex

/**
 * Extensions for working with Android Dimensions
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
private fun getScale() =
        Kex.instance.context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

fun Int.asDpToPx() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Kex.instance.context.resources.displayMetrics)
fun Float.asDpToPx() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Kex.instance.context.resources.displayMetrics)

fun Int.asDpToPxInt() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Kex.instance.context.resources.displayMetrics).toInt()
fun Float.asDpToPxInt() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Kex.instance.context.resources.displayMetrics).toInt()

fun Int.asPxToDp(context: Context) = this / getScale()
fun Float.asPxToDp(context: Context) = this / getScale()