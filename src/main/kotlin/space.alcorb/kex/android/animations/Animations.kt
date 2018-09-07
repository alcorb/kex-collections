package space.alcorb.kex.android.animations

import android.animation.ObjectAnimator
import android.view.animation.AccelerateInterpolator
import android.widget.TextView

/**
 * Extensions for Android animations.
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
fun TextView.expandToLines(lineCount: Int) {
    ObjectAnimator.ofInt(
        this,
        "maxLines",
        lineCount
    ).apply {
        duration = 300
        interpolator = AccelerateInterpolator()
    }.start()
}

fun TextView.collapseToLines(lineCount: Int) {
    ObjectAnimator.ofInt(
        this,
        "maxLines",
        lineCount
    ).apply {
        duration = 300
        interpolator = AccelerateInterpolator()
    }.start()
}