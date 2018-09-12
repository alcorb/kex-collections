package space.alcorb.kex.stubs

import android.databinding.ViewDataBinding

/**
 * @author Yamushev Igor
 * @since  12.09.18
 */
abstract class StubHolder<in B : ViewDataBinding> {
    abstract val layout: Int
    abstract fun onBind(binding: B)
}