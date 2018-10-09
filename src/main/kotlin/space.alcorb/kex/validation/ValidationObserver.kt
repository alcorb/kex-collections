package space.alcorb.kex.validation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Validator for text inputs.
 *
 * @Usage: Example: val validators = listOf(binding.etProductListName.isNotEmpty())
                    lifecycle.addObserver(
                        ValidationObserver(validators) {
                            binding.btnSaveNewProductList.isEnabled = it
                        }
                    )
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
class ValidationObserver(
    private val validators: List<BehaviorSubject<Boolean>>,
    private val callback: (Boolean) -> Unit
) : LifecycleObserver {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startValidate() {
        compositeDisposable = CompositeDisposable()
        validate(validators, callback)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopValidate() {
        compositeDisposable.clear()
    }

    private fun validate(validators: List<BehaviorSubject<Boolean>>, callback: (Boolean) -> Unit) {
        val subs = Observable
            .combineLatest(validators) { !it.contains(false) }
            .subscribe({ callback.invoke(it) }, { })
        compositeDisposable.add(subs)
    }
}