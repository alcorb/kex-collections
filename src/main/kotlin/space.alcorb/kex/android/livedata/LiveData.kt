package space.alcorb.kex.android.livedata

import android.arch.lifecycle.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

/**
 * Extensions for Android Live Data.
 *
 * @author Yamushev Igor
 * @since 06.09.18
 */
fun <T : ViewModel> FragmentActivity.viewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}

fun <T : ViewModel> Fragment.viewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (value: T?) -> Unit) {
    this.observe(lifecycleOwner, Observer(observer::invoke))
}

fun <T> liveDataOf(defValue: T? = null) = MutableLiveData<T>().apply {
    defValue?.apply { postValue(this) }
}