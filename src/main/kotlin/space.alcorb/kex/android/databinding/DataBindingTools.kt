package space.alcorb.kex.android.databinding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import space.alcorb.kex.android.views.hide
import space.alcorb.kex.android.views.show
import space.alcorb.kex.prinitives.fromHtml

/**
 * Tools for Adnroid data binding.
 *
 * @using: import DataBindingTools to your layout file and user methods as @{DataBindingTools.fun()}
 * @author Yamushev Igor
 * @since 06.09.18
 */
object DataBindingTools {
    
    @JvmStatic
    fun isNull(obj: Any?) = obj == null
    
    @JvmStatic
    fun isNullOrEmpty(text: String?) = text.isNullOrEmpty()
    

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun setVisibilityByCondition(view: View, condition: Boolean) {
        if (condition) view.show() else view.hide()
    }
    
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun setImageUrl(view: ImageView, url: String?, placeHolder: Int?) {
        if (url == null) return
        if (placeHolder != null) {
            Glide.with(view.context)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(placeHolder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontTransform()
                        .dontAnimate()
                )
                .into(view)
        } else {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
    }
    
    @JvmStatic
    @BindingAdapter("htmlText")
    fun setHtmlStringToView(textView: TextView, htmlString: String?) {
        textView.text = htmlString?.fromHtml()
    }
}