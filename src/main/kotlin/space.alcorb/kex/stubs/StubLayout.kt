package space.alcorb.kex.stubs

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import space.alcorb.kex.android.views.hide

/**
 * @author Yamushev Igor
 * @since  12.09.18
 */
class StubLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {
    
    private var contentView: View? = null
    
    private var currentHolderClass: String? = null
    
    fun <B : ViewDataBinding> showStub(holder: StubHolder<B>) {
        while (childCount != 1) removeViewAt(1)
        
        currentHolderClass = holder::class.java.simpleName
        
        contentView?.hide()
        
        val binding = DataBindingUtil.inflate<B>(LayoutInflater.from(context), holder.layout, this, false)
        addView(binding.root, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        holder.onBind(binding)
    }
    
    fun showContent() {
        while (childCount != 1)
            removeViewAt(1)
        contentView?.visibility = View.VISIBLE
    }
    
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (contentView == null)
            contentView = child
        super.addView(child, index, params)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        for (i in 0 until childCount) getChildAt(i).dispatchApplyWindowInsets(insets)
        return insets
    }
    
    fun isCurrentLoading(): Boolean =
        childCount != 1 && currentHolderClass == LoadingStubHolder::class.java.simpleName
}