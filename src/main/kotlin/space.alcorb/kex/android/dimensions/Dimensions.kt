package space.alcorb.kex.android.dimensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Extensions for working with Android Dimensions
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
private fun getScale(context: Context) = context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

fun Int.asDpToPx(context: Context) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics)
fun Float.asDpToPx(context: Context) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

fun Int.asPxToDp(context: Context) = this / getScale(context)
fun Float.asPxToDp(context: Context) = this / getScale(context)