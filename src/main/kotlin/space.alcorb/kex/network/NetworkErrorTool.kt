package space.alcorb.kex.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Tool for creating Api Errors.
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
abstract class NetworkErrorTool<ErrorBodyType>(
    gson: Gson,
    errorTypeKlass: Class<ErrorBodyType>
) {
    private val jsonConverter: Converter<ResponseBody, ErrorBodyType> =
            GsonConverterFactory
                .create(gson)
                .responseBodyConverter(errorTypeKlass,
                                       arrayOf(),
                                       null) as Converter<ResponseBody, ErrorBodyType>
    
    fun convertErrorBodyToObject(response: ResponseBody): ErrorBodyType = jsonConverter.convert(response)
    
    abstract fun createApiExceptionFrom(code: Int, errorResponse: ErrorBodyType?): ApiException
}