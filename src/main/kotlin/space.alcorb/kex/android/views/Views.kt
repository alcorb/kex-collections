package space.alcorb.kex.android.views

import android.support.v7.widget.SearchView
import android.text.*
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Extensions for Android views.
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
fun TextView.onTextChanged(
    after: (s: Editable) -> Unit = {},
    before: (string: String, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    onTextChanged: (string: String, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> Unit }
) =
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = after.invoke(s)
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) =
                    before.invoke(s.toString(), start, count, after)

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) =
                    onTextChanged(s.toString(), start, before, count)
        })

fun TextView.doAfterTextChanged(f: (s: Editable) -> Unit) = this.onTextChanged(after = f)

fun TextView.doBeforeTextChanged(f: (string: String, start: Int, count: Int, after: Int) -> Unit) = this.onTextChanged(before = f)

fun TextView.doOnTextChanged(f: (string: String, start: Int, count: Int, after: Int) -> Unit) = this.onTextChanged(onTextChanged = f)

fun TextView.textOrNull(): String? =
        if (text.toString().isEmpty()) null else text.toString()

fun View.hide() {
    this.visibility = View.GONE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun <B : View> ViewGroup.forEachChild(function: B.() -> Unit) {
    for (i in 0 until childCount) {
        try {
            val v = getChildAt(i) as B
            function(v)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }
}

fun SearchView.onQuery(
    onQueryTextSubmit: (String?) -> Unit,
    onQueryTextChange: (String) -> Unit
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean{
            onQueryTextSubmit(query)
            return true
        }
        
        override fun onQueryTextChange(newText: String?): Boolean {
            onQueryTextChange(newText ?: "")
            return true
        }
    })
}

inline fun SpannableStringBuilder.setClickSpanWithColor(crossinline onClick: () -> Unit, spanEntry: String, color: Int) {
    val start = indexOf(spanEntry)
    val end = start + spanEntry.length
    
    val clickSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint?) {
            ds?.color = color
            ds?.isUnderlineText = false
        }
        
        override fun onClick(widget: View?) {
            onClick.invoke()
        }
    }
    
    setSpan(clickSpan,
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    
}