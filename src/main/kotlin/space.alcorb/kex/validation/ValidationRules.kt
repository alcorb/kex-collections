package space.alcorb.kex.validation

import android.widget.TextView
import io.reactivex.subjects.BehaviorSubject
import space.alcorb.kex.android.views.doAfterTextChanged

/**
 * Rules for validating inputs.
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
fun TextView.isNotEmpty(onError: ((Boolean) -> Unit)? = null): BehaviorSubject<Boolean> {
    val subject = BehaviorSubject.createDefault(false)
    this.doAfterTextChanged {
        subject.onNext(it.isNotEmpty())
    }
    return subject
}

fun TextView.isRusMobilePhoneNumberValid(onError: ((Boolean) -> Unit)?): BehaviorSubject<Boolean> {
    val subject = BehaviorSubject.createDefault(false)
    this.doAfterTextChanged {
        subject.onNext(!it.isNullOrEmpty() && it.replace(Regex("[\\+\\-\\s]"), "").length == 11)
    }
    return subject
}