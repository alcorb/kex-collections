package space.alcorb.kex.android.databinding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import space.alcorb.kex.android.dimensions.asDpToPx
import space.alcorb.kex.android.views.hide
import space.alcorb.kex.android.views.show
import space.alcorb.kex.primitives.fromHtml

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
    @BindingAdapter(value = ["imageUrl", "placeHolder", "cornerRadius", "isCircle"], requireAll = false)
    fun setImageUrl(view: ImageView, url: String?, placeHolder: Int?, cornerRadius: Int?, isCircle: Boolean?) {
        if (url == null) return
        
        val requestOptions = RequestOptions()
        
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
        
        if (placeHolder != null) requestOptions.placeholder(placeHolder)
        if (cornerRadius != null) requestOptions.transform(RoundedCorners(cornerRadius.asDpToPx().toInt()))
        if (isCircle == true) requestOptions.circleCrop()
    
        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(view)
    }
    
    @JvmStatic
    @BindingAdapter("htmlText")
    fun setHtmlStringToView(textView: TextView, htmlString: String?) {
        textView.text = htmlString?.fromHtml()
    }
}