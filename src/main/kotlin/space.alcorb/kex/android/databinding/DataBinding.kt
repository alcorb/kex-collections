package space.alcorb.kex.android.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Extensions for Android DataBinding.
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
fun <T : ViewDataBinding> ViewGroup.inflate(@LayoutRes layout: Int) =
        DataBindingUtil.inflate<T>(
            LayoutInflater.from(context),
            layout,
            this,
            true
        )