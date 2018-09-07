package space.alcorb.kex.network

import com.google.gson.JsonSyntaxException
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException

/**
 * Extensions for network actions
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
fun <T, ErrorBodyType> Single<Response<T>>.asKexRetrofitResponse(
    errorTool: NetworkErrorTool<ErrorBodyType>?,
    errorTracker: NetworkErrorTracker?,
    parsingErrorString: String,
    connectionErrorString: String
) =
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
                            errorTracker?.track(urlPath, "Error convert Exception at request", e)
                        }
                    }
                    val e = errorTool?.createApiExceptionFrom(it.code(), errorResponse)
                    errorTracker?.track(urlPath, "Error at request", e!!)
                    throw e!!
                }
                it
            }
            .onErrorResumeNext {
                when (it) {
                    is JsonSyntaxException -> {
                        it.printStackTrace()
                        Single.error(ApiException(parsingErrorString))
                    }
                    is IOException -> {
                        errorTracker?.track(null, "No Connection Ex at request", it)
                        Single.error(ApiException(connectionErrorString))
                    }
                    else -> {
                        it.printStackTrace()
                        Single.error(it)
                    }
                }
            }

fun <ErrorBodyType> Single<Response<Void>>.asKexVoid(
    errorTool: NetworkErrorTool<ErrorBodyType>?,
    errorTracker: NetworkErrorTracker?,
    parsingErrorString: String,
    connectionErrorString: String
) =
        this
            .asKexRetrofitResponse(errorTool, errorTracker, parsingErrorString, connectionErrorString)
            .observeOn(AndroidSchedulers.mainThread())

fun <T, ErrorBodyType> Single<Response<T>>.asKexBody(
    errorTool: NetworkErrorTool<ErrorBodyType>?,
    errorTracker: NetworkErrorTracker?,
    parsingErrorString: String,
    connectionErrorString: String,
    emptyBodyResponseString: String,
    observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
) =
        this
            .asKexRetrofitResponse(errorTool, errorTracker, parsingErrorString, connectionErrorString)
            .map {
                if (it.body() == null) {
                    val e = ApiException(emptyBodyResponseString)
                    errorTracker?.track(null, "API Ex at request", e)
                    throw e
                }
                it.body()!!
            }
            .onErrorResumeNext { Single.error(it) }
            .observeOn(observeOnScheduler)
