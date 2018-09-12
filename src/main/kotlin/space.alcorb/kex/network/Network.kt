package space.alcorb.kex.network

import com.google.gson.JsonSyntaxException
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import space.alcorb.kex.Kex
import java.io.IOException

/**
 * Extensions for network actions
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
fun <T, ErrorBodyType> Single<Response<T>>.asRetrofitResponse(errorTool: NetworkErrorTool<ErrorBodyType>?) =
        this
            .subscribeOn(Schedulers.io())
            .map {
                val urlPath = it.raw().request().url().encodedPath()
                if (!it.isSuccessful) {
                    var errorResponse: ErrorBodyType? = null
                    if (it.errorBody() != null && errorTool != null) {
                        try {
                            errorResponse = errorTool.convertErrorBodyToObject(it.errorBody()!!)
                        } catch(e: Exception) {
                            e.printStackTrace()
                            Kex.instance.networkData.errorTracker?.track(urlPath, "Error convert Exception at request", e)
                        }
                    }
                    val e = errorTool?.createApiExceptionFrom(it.code(), errorResponse)
                    Kex.instance.networkData.errorTracker?.track(urlPath, "Error at request", e!!)
                    throw e!!
                }
                it
            }
            .onErrorResumeNext {
                when (it) {
                    is JsonSyntaxException -> {
                        it.printStackTrace()
                        Single.error(ApiException(Kex.instance.networkData.parsingErrorString))
                    }
                    is IOException -> {
                        Kex.instance.networkData.errorTracker?.track(null, "No Connection Ex at request", it)
                        Single.error(ApiException(Kex.instance.networkData.connectionErrorString))
                    }
                    else -> {
                        it.printStackTrace()
                        Single.error(it)
                    }
                }
            }

fun <T, ErrorBodyType> Single<Response<T>>.asVoid(
    errorTool: NetworkErrorTool<ErrorBodyType>?
) =
        this
            .asRetrofitResponse(errorTool)
            .observeOn(AndroidSchedulers.mainThread())

fun <T, ErrorBodyType> Single<Response<T>>.asBody(errorTool: NetworkErrorTool<ErrorBodyType>?,
                                                  observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()) =
        this
            .asRetrofitResponse(errorTool)
            .map {
                if (it.body() == null) {
                    val e = ApiException(Kex.instance.networkData.emptyBodyResponseString)
                    Kex.instance.networkData.errorTracker?.track(null, "API Ex at request", e)
                    throw e
                }
                it.body()!!
            }
            .onErrorResumeNext { Single.error(it) }
            .observeOn(observeOnScheduler)
