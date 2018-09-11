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
private val RUS_PHONE_EXTRA_SYMBOLS_REG = Regex("[\\+\\-\\s]")

fun TextView.isNotEmpty(onError: ((Boolean) -> Unit)? = null): BehaviorSubject<Boolean> {
    val subject = BehaviorSubject.createDefault(this.text.isNotEmpty())
    this.doAfterTextChanged {
        subject.onNext(it.isNotEmpty())
    }
    return subject
}

fun TextView.isRusMobilePhoneNumberValid(onError: ((Boolean) -> Unit)?): BehaviorSubject<Boolean> {
    val subject = BehaviorSubject.createDefault(!this.text.isNullOrEmpty() && this.text.replace(RUS_PHONE_EXTRA_SYMBOLS_REG, "").length == 11)
    this.doAfterTextChanged {
        subject.onNext(!it.isNullOrEmpty() && it.replace(RUS_PHONE_EXTRA_SYMBOLS_REG, "").length == 11)
    }
    return subject
}